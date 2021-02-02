//package com.ltframework.boot.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ltframework.boot.constant.SysExceptionConstant;
//import com.ltframework.boot.exception.SysException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.geotools.data.simple.SimpleFeatureIterator;
//import org.geotools.feature.FeatureCollection;
//import org.geotools.geojson.feature.FeatureJSON;
//import org.geotools.geojson.geom.GeometryJSON;
//import org.geotools.geometry.jts.JTSFactoryFinder;
//import org.locationtech.jts.geom.Geometry;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.locationtech.jts.geom.Point;
//import org.locationtech.jts.geom.Polygon;
//import org.locationtech.jts.io.WKTReader;
//import org.opengis.feature.simple.SimpleFeature;
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//public class MapUtil {
//    //多边形转geometry
//    public static  String covertGeoJSONtoGemory(String gpsFence) throws IOException {
//        FeatureJSON fjson_15 = new FeatureJSON(new GeometryJSON(15));
//        FeatureCollection featureCollection = fjson_15.readFeatureCollection(gpsFence);
//        SimpleFeatureIterator iterator = (SimpleFeatureIterator) featureCollection.features();
//        SimpleFeature simpleFeature = iterator.next();
//        Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
//        return geometry.toText();
//    }
//    /*点读取经纬度*/
//    public static  String covertGeoJSONtoPoint(String geoJson) throws Exception{
//       GeometryJSON geometryJSON= new GeometryJSON(15);
//       Point point= geometryJSON.readPoint(geoJson);
//       return point.toText();
//    }
//   //数据库取多边形经纬度
//    public static String evaluate(String str) {
//        String json= null;
//        try {
//            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
//            WKTReader reader = new WKTReader(geometryFactory);
//            System.out.println("poloygon="+str);
//            Polygon polygon=(Polygon)reader.read(str);
//            GeometryJSON geometryJson = new GeometryJSON(15);
//            StringWriter writer = new StringWriter();
//            geometryJson.write(polygon, writer);
//            System.out.println(writer.toString());
//            writer.close();
//         /*   JSONObject jsonObject=new JSONObject();
//            jsonObject.put("type","Feature");
//            jsonObject.put("geometry",writer);
//            jsonObject.put("properties",new JSONObject());*/
//            json=writer.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return json;
//    }
//
//    //数据库取点经纬度
//    public static String evaluatePoint(String str) {
//        String json= null;
//        try {
//            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
//            WKTReader reader = new WKTReader(geometryFactory);
//            System.out.println("poloygon="+str);
//            Point point=(Point) reader.read(str);
//            GeometryJSON geometryJson = new GeometryJSON(15);
//            StringWriter writer = new StringWriter();
//            geometryJson.write(point, writer);
//            System.out.println(writer.toString());
//            writer.close();
//         /*   JSONObject jsonObject=new JSONObject();
//            jsonObject.put("type","Feature");
//            jsonObject.put("geometry",writer);
//            jsonObject.put("properties",new JSONObject());*/
//            json=writer.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return json;
//    }
//
//    /**将一维数组解析成点经纬度*/
//    public static String handleArrayToPoint(List<Number> pointArray){
//        /**处理经纬度*/
//        String pointLongLat = null;
//        if (pointArray.size() > 0) {
//            JSONObject pointObject = new JSONObject();
//            pointObject.put("type", "Feature");
//            JSONObject geometry = new JSONObject();
//            geometry.put("type", "Point");
//            geometry.put("properties", new JSONObject());
//            geometry.put("coordinates", pointArray);
//            pointObject.put("geometry", geometry);
//            String str = JSON.toJSONString(pointObject);
//            try {
//                pointLongLat = covertGeoJSONtoPoint(str);
//            }
//            catch (Exception e) {
//               log.error("经纬度解析异常，" + e.getMessage());
//               throw new SysException(SysExceptionConstant.ANALYSIS_ERROR_LONG_LAT);
//            }
//        }
//        return pointLongLat;
//    }
//
//    /**将一维数组解析成点经纬度*/
//    public static List<Number> handlePointToArray(String geoJson){
//        if(StringUtils.isEmpty(geoJson)){
//            return new ArrayList<>();
//        }
//        String point=MapUtil.evaluatePoint(geoJson);
//        JSONObject pointJson= JSONObject.parseObject(point);
//        String coordinates=pointJson.getString("coordinates");
//        System.out.println("coordinates="+coordinates);
//        List<Number> longLat= JSONArray.parseArray(coordinates,Number.class);
//        return longLat;
//    }
//
//    /**将多维数组解析成点gpsFence*/
//    public static String handleMulitArrayToFence(List<List<Number>> fenceArray) throws Exception {
//        /**处理经纬度*/
//        String fenceLongLat = "";
//        if (fenceArray.size() > 0) {
//            JSONObject fenceObject = new JSONObject();
//            fenceObject.put("type", "FeatureCollection");
//            JSONArray featureArray=new JSONArray();
//            JSONObject feature = new JSONObject();
//            feature.put("type", "Feature");
//            feature.put("properties", new JSONObject());
//            JSONObject geometry=new JSONObject();
//            geometry.put("type", "Polygon");
//            geometry.put("coordinates", fenceArray);
//            feature.put("geometry", geometry);
//            featureArray.add(feature);
//            fenceObject.put("features",featureArray);
//            String str = JSON.toJSONString(fenceObject);
//            fenceLongLat = covertGeoJSONtoGemory(str);
//        }
//        return fenceLongLat;
//    }
//
//    /**将一维数组解析成点经纬度*/
//    public static List<List<Number>> handleFenceToMuiltArray(String geoJson) throws Exception {
//        if(StringUtils.isEmpty(geoJson)){
//            return new ArrayList<>();
//        }
//        String fence=MapUtil.evaluate(geoJson);
//        JSONObject fenceJson= JSONObject.parseObject(fence);
//        String coordinates=fenceJson.getString("coordinates");
//        JSONArray jsonArray= fenceJson.getJSONArray("coordinates");
//        System.out.print(jsonArray);
//        List<List<Number>> fenceList=(List<List<Number>>)jsonArray.get(0);
//        return fenceList;
//    }
//
//}
