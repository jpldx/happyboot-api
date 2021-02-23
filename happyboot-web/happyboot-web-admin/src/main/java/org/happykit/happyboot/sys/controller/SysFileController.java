package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.constant.FileConstant;
import org.happykit.happyboot.exception.BusinessException;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.log.annotation.Log;
import org.happykit.happyboot.sys.model.entity.SysFileDO;
import org.happykit.happyboot.sys.model.query.SysFilePageQueryParam;
import org.happykit.happyboot.sys.service.SysConfigService;
import org.happykit.happyboot.sys.service.SysFileService;
import org.happykit.happyboot.util.IdUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文件上传
 *
 * @author shaoqiang
 * @version 1.0 2020/3/27
 */
@Validated
@Slf4j
@Controller
@RequestMapping("sys/file")
public class SysFileController extends BaseController {

    private final SysFileService sysFileService;
    private final SysConfigService sysConfigService;

    public SysFileController(SysFileService sysFileService, SysConfigService sysConfigService) {
        this.sysFileService = sysFileService;
        this.sysConfigService = sysConfigService;
    }

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @Log("文件-分页列表")
    @GetMapping("/page")
    @ResponseBody
    public R page(@Valid SysFilePageQueryParam query) {
        return success(sysFileService.page(query));
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Log("文件-上传")
    @PostMapping("/uploadFile")
    @ResponseBody
    public R uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return failed("文件不能为空");
        }
        // 存储路径
        String storePath = sysConfigService.getValueByKey(FileConstant.STORE_PATH);
        if (StringUtils.isBlank(storePath)) {
            return failed("系统未设置存储路径");
        }
        // 允许上传文件后缀
        String fileExt = sysConfigService.getValueByKey(FileConstant.FILE_EXT);
        if (StringUtils.isBlank(fileExt)) {
            return failed("系统未设置允许上传的文件格式");
        }
        // 上传文件限制大小
        String fileSize = sysConfigService.getValueByKey(FileConstant.FILE_SIZE);
        if (StringUtils.isBlank(fileExt)) {
            return failed("系统未设置上传文件限制大小");
        }

        if (file.getSize() > Long.parseLong(fileSize)) {
            return failed("上传文件大小超过限制大小");
        }

        // 获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        // 设置文件后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 设置文件后缀名，不加.
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        // 判断是否允许
        String[] fileExtArr = fileExt.split(",");
        List list = Arrays.asList(fileExtArr);
        if (!list.contains(suffix)) {
            return failed("非法文件类型");
        }

        // 文件路径是在 根目录下加上日期 文件名加上日期的long
        String destPath = storePath + File.separatorChar + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separatorChar;
        String id = IdUtils.simpleUUID();
        String destFileName = id + suffixName;
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        dest = new File(dest, destFileName);
        // 执行上传
        FileCopyUtils.copy(file.getBytes(), dest);

        log.info("上传的文件名为：" + originalFilename + "后缀名为" + suffixName);

        SysFileDO entity = new SysFileDO();
        entity.setFileName(originalFilename);
        entity.setFileAliasName(destFileName);
        entity.setFileSize(file.getSize());
        entity.setFilePath(dest.getPath());

        Path path = Paths.get(dest.getPath());
        Files.probeContentType(path);
        entity.setMime(Files.probeContentType(path));

        String hex = DigestUtils.sha512Hex(new FileInputStream(dest));
        String md5 = DigestUtils.md5Hex(new FileInputStream(dest));
        entity.setSha1(hex);
        entity.setMd5(md5);
        sysFileService.save(entity);
        return success(entity.getId().toString());
    }

    /**
     * 多文件上传
     *
     * @param file 文件数组
     * @return
     * @throws IOException
     */
    @Log("文件-批量上传")
    @Transactional
    @PostMapping("/uploadFiles")
    @ResponseBody
    public R uploadFiles(@RequestParam(name = "file") MultipartFile[] files) throws IOException {
        int uploadFileAmount = files.length;
        if (uploadFileAmount == 0) {
            return failed("至少上传一个文件");
        }
        // 数量限制
        String fileAmount = sysConfigService.getValueByKey(FileConstant.FILE_AMOUNT);
        if (StringUtils.isBlank(fileAmount)) {
            return failed("系统未设置允许上传的文件数量");
        }
        if (uploadFileAmount > Integer.parseInt(fileAmount)) {
            return failed("上传的文件数量超过限制（" + fileAmount + "）");
        }
        // 存储路径
        String storePath = sysConfigService.getValueByKey(FileConstant.STORE_PATH);
        if (StringUtils.isBlank(storePath)) {
            return failed("系统未设置存储路径");
        }
        // 后缀限制
        String fileExt = sysConfigService.getValueByKey(FileConstant.FILE_EXT);
        if (StringUtils.isBlank(fileExt)) {
            return failed("系统未设置允许上传的文件格式");
        }
        List fileExtList = Arrays.asList(fileExt.split(","));
        // 大小限制
        String fileSizeStr = sysConfigService.getValueByKey(FileConstant.FILE_SIZE);
        if (StringUtils.isBlank(fileExt)) {
            return failed("系统未设置上传文件限制大小");
        }
        long fileSize = Long.parseLong(fileSizeStr);

        List<String> fileIds = new ArrayList<>();
        // 校验文件是否合法
        for (MultipartFile file : files) {
            // 文件的原始名称
            String originalFilename = file.getOriginalFilename();
            // 文件后缀名
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 文件后缀名，不加.
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (file.isEmpty()) {
                throw new SysException("文件内容不能为空");
            }
            if (file.getSize() > fileSize) {
                throw new SysException("上传文件大小超过限制大小（" + (fileSize / 1024 / 1024) + "M）");
            }
            if (!fileExtList.contains(suffix)) {
                throw new SysException("文件类型非法");
            }
            // 保存文件 文件路径是在 根目录下加上日期 文件名加上日期的long
            String destPath = storePath +
                    File.separatorChar +
                    new SimpleDateFormat("yyyyMMdd").format(new Date()) +
                    File.separatorChar;
            String id = IdUtils.simpleUUID();
            String destFileName = id + suffixName;
            File dest = new File(destPath);
            if (!dest.exists()) {
                dest.mkdirs();
            }
            dest = new File(dest, destFileName);
            // 执行上传
            FileCopyUtils.copy(file.getBytes(), dest);

            log.info("上传的文件名为：" + originalFilename + "后缀名为" + suffixName);

            SysFileDO entity = new SysFileDO();
            entity.setFileName(originalFilename);
            entity.setFileAliasName(destFileName);
            entity.setFileSize(file.getSize());
            entity.setFilePath(dest.getPath());

            Path path = Paths.get(dest.getPath());
            Files.probeContentType(path);
            entity.setMime(Files.probeContentType(path));

            String hex = DigestUtils.sha512Hex(new FileInputStream(dest));
            String md5 = DigestUtils.md5Hex(new FileInputStream(dest));
            entity.setSha1(hex);
            entity.setMd5(md5);
            sysFileService.save(entity);
            fileIds.add(entity.getId());
        }
        return success(fileIds);
    }

    /**
     * 文件下载
     *
     * @param id
     * @return
     * @throws IOException
     */
    @Log("文件-下载")
    @GetMapping("/download/{id}")
    public ResponseEntity download(@PathVariable Long id) throws IOException {
        SysFileDO sysFile = sysFileService.getById(id);
        if (sysFile == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
//            throw new SysException("未找到相关记录");
        }
        File file = new File(sysFile.getFilePath());
        if (!file.exists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
//            throw new SysException("未找到相关文件");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("FILE_NAME", sysFile.getFileName());
        headers.set("Access-Control-Expose-Headers", "FILE_NAME");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", sysFile.getFileAliasName());
        return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    /**
     * 图片上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Log("图片-上传")
    @PostMapping("/uploadImg")
    @ResponseBody
    public R uploadImg(@RequestParam(value = "file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return failed("文件不能为空");
        }
        // 存储路径
        String storePath = sysConfigService.getValueByKey(FileConstant.STORE_PATH);
        if (StringUtils.isBlank(storePath)) {
            return failed("系统未设置存储路径");
        }
        // 允许上传图片后缀
        String fileExt = sysConfigService.getValueByKey(FileConstant.IMAGE_EXT);
        if (StringUtils.isBlank(fileExt)) {
            return failed("系统未设置允许上传的文件格式");
        }
        // 上传文件限制大小
        String fileSize = sysConfigService.getValueByKey(FileConstant.FILE_SIZE);
        if (StringUtils.isBlank(fileExt)) {
            return failed("系统未设置上传文件限制大小");
        }

        if (file.getSize() > Long.parseLong(fileSize)) {
            return failed("上传文件大小超过限制大小");
        }

        // 获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        // 设置文件后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 设置文件后缀名，不加.
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        // 判断是否允许
        String[] fileExtArr = fileExt.split(",");
        List list = Arrays.asList(fileExtArr);
        if (!list.contains(suffix)) {
            return failed("非法文件类型");
        }

        // 文件路径是在 根目录下加上日期 文件名加上日期的long
        String destPath = storePath + File.separatorChar + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separatorChar;
        String id = IdUtils.simpleUUID();
        String destFileName = id + suffixName;
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        dest = new File(dest, destFileName);
        // 执行上传
        FileCopyUtils.copy(file.getBytes(), dest);

        log.info("上传的文件名为：" + originalFilename + "后缀名为" + suffixName);

        SysFileDO entity = new SysFileDO();
        entity.setFileName(originalFilename);
        entity.setFileAliasName(destFileName);
        entity.setFileSize(file.getSize());
        entity.setFilePath(dest.getPath());

        Path path = Paths.get(dest.getPath());
        Files.probeContentType(path);
        entity.setMime(Files.probeContentType(path));

        String hex = DigestUtils.sha512Hex(new FileInputStream(dest));
        String md5 = DigestUtils.md5Hex(new FileInputStream(dest));
        entity.setSha1(hex);
        entity.setMd5(md5);
        sysFileService.save(entity);
        return success(entity.getId().toString());
    }

    /**
     * 多图片上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Log("图片-批量上传")
    @Transactional
    @PostMapping("/uploadImgs")
    @ResponseBody
    public R uploadImg(@RequestParam(value = "file") MultipartFile[] files) throws IOException {
        int uploadFileAmount = files.length;
        if (uploadFileAmount == 0) {
            return failed("至少上传一个文件");
        }
        // 数量限制
        String fileAmount = sysConfigService.getValueByKey(FileConstant.FILE_AMOUNT);
        if (StringUtils.isBlank(fileAmount)) {
            return failed("系统未设置允许上传的文件数量");
        }
        if (uploadFileAmount > Integer.parseInt(fileAmount)) {
            return failed("上传的文件数量超过限制（" + fileAmount + "）");
        }
        // 存储路径
        String storePath = sysConfigService.getValueByKey(FileConstant.STORE_PATH);
        if (StringUtils.isBlank(storePath)) {
            return failed("系统未设置存储路径");
        }
        // 后缀限制
        String fileExt = sysConfigService.getValueByKey(FileConstant.IMAGE_EXT);
        if (StringUtils.isBlank(fileExt)) {
            return failed("系统未设置允许上传的文件格式");
        }
        List fileExtList = Arrays.asList(fileExt.split(","));
        // 大小限制
        String fileSizeStr = sysConfigService.getValueByKey(FileConstant.FILE_SIZE);
        if (StringUtils.isBlank(fileExt)) {
            return failed("系统未设置上传文件限制大小");
        }
        long fileSize = Long.parseLong(fileSizeStr);

        List<String> fileIds = new ArrayList<>();
        for (MultipartFile file : files) {
            // 获取文件的原始名称
            String originalFilename = file.getOriginalFilename();
            // 设置文件后缀名
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 设置文件后缀名，不加.
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (file.isEmpty()) {
                throw new SysException("文件内容不能为空");
            }
            if (file.getSize() > fileSize) {
                throw new SysException("上传文件大小超过限制大小（" + (fileSize / 1024 / 1024) + "M）");
            }
            if (!fileExtList.contains(suffix)) {
                return failed("非法文件类型");
            }
            // 文件路径是在 根目录下加上日期 文件名加上日期的long
            String destPath = storePath +
                    File.separatorChar +
                    new SimpleDateFormat("yyyyMMdd").format(new Date()) +
                    File.separatorChar;
            String id = IdUtils.simpleUUID();
            String destFileName = id + suffixName;
            File dest = new File(destPath);
            if (!dest.exists()) {
                dest.mkdirs();
            }
            dest = new File(dest, destFileName);
            // 执行上传
            FileCopyUtils.copy(file.getBytes(), dest);

            log.info("上传的文件名为：" + originalFilename + "后缀名为" + suffixName);

            SysFileDO entity = new SysFileDO();
            entity.setFileName(originalFilename);
            entity.setFileAliasName(destFileName);
            entity.setFileSize(file.getSize());
            entity.setFilePath(dest.getPath());

            Path path = Paths.get(dest.getPath());
            Files.probeContentType(path);
            entity.setMime(Files.probeContentType(path));

            String hex = DigestUtils.sha512Hex(new FileInputStream(dest));
            String md5 = DigestUtils.md5Hex(new FileInputStream(dest));
            entity.setSha1(hex);
            entity.setMd5(md5);
            sysFileService.save(entity);
            fileIds.add(entity.getId());
        }
        return success(fileIds);
    }


    /**
     * 查看原图
     *
     * @param id
     * @return
     * @throws IOException
     */
    @Log("图片-查看")
    @GetMapping("/img/{id}")
    public ResponseEntity img(@PathVariable Long id) throws IOException {
        SysFileDO sysFile = sysFileService.getById(id);
        if (sysFile == null) {
            return null;
        }
        // 载入图像
        BufferedImage buffImg = ImageIO.read(new FileInputStream(new File(sysFile.getFilePath())));
        byte[] imageContent = fileToByte(buffImg);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    /**
     * 图片预览
     *
     * @param id 图片id
     * @return
     * @throws IOException
     */
    @Log("图片-预览")
    @GetMapping("/view/{id}")
    public ResponseEntity view(@PathVariable Long id) {
        SysFileDO sysFile = sysFileService.getById(id);
        if (sysFile == null) {
            return null;
        }
        // 载入图像
        File file = new File(sysFile.getFilePath());
        byte[] bytes;
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BusinessException("未找到相关文件");
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setCacheControl("max-age=604800");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    /**
     * 缩略图预览
     *
     * @param id
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    @Log("图片-缩略图预览")
    @GetMapping(value = "/thumbnail/{id}")
    public ResponseEntity thumbnail(
            @PathVariable Long id, @RequestParam(value = "width", defaultValue = "148") Integer width, @RequestParam(value = "height", defaultValue = "148") Integer height) {
        SysFileDO sysFile = sysFileService.getById(id);
        if (sysFile == null) {
            return null;
        }
        BufferedImage buffImg;
        byte[] imageContent;
        try {
            buffImg = Thumbnails.of(new File(sysFile.getFilePath())).size(width, height).asBufferedImage();
            imageContent = fileToByte(buffImg);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BusinessException("未找到相关文件");
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    private byte[] fileToByte(BufferedImage bufferedImage) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpeg", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }

}
