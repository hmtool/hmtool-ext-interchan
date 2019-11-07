package tech.mhuang.ext.interchan.protocol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用API忽略
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class BaseApiIgnore {

    @ApiModelProperty(value = "用户id", hidden = true)
    private String userId;
}
