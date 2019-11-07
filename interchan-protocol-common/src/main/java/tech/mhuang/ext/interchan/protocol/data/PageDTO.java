package tech.mhuang.ext.interchan.protocol.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页DTO
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class PageDTO {

    @ApiModelProperty(value = "每页行数,默认10行", required = true)
    Integer rows = 10;

    @ApiModelProperty(value = "开始页数", required = true)
    Integer start = 1;
}
