package org.happykit.happyboot.model.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 获取ID集合模型
 *
 * @author shaoqiang
 * @version 1.0 2020/5/18
 */
@Data
public class IdsModel implements Serializable {
	/**
	 * ID集合
	 */
	@NotEmpty(message = "ID集合必须填")
	private String[] ids;
}
