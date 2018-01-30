package ${packagePath};

import com.nonpool.annotation.HandlerMapping;
import com.nonpool.proto.${className};
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
@HandlerMapping("${className}")
public class ${className}Handler extends AbstractDataHandler<${className}> {
    @Override
    public void onEvent(${className} ${lowerClassName}, ChannelHandlerContext ctx) throws Exception {

    }
}