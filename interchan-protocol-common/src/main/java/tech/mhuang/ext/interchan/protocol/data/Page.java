package tech.mhuang.ext.interchan.protocol.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询对象
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Page<T> extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private T record;
}
