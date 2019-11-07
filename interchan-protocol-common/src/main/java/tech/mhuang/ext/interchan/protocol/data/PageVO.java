package tech.mhuang.ext.interchan.protocol.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 分页应答
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {

    /**
     * 总行数
     */
    @ApiModelProperty(value = "总条数")
    private int totalSize;

    /**
     * 业务数据 <LIST>
     */
    @ApiModelProperty(value = "业务数据")
    private List<T> result = Collections.emptyList();
}
