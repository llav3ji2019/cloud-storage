package decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class RequestDataDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if (msg.readableBytes() == 0) {
            return;
        }
        byte[] data = new byte[msg.readInt()];
        msg.readBytes(data);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            try (ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream)) {
                out.add(inputStream.readObject());
            }
        }
    }
}
