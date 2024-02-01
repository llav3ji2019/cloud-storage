package encoder;

import structures.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class RequestDataEncode extends MessageToByteEncoder<RequestData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestData msg, ByteBuf out) throws Exception {
        if (msg == null) {
            return;
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                outputStream.writeObject(msg);

                out.writeInt(byteArrayOutputStream.size());
                out.writeBytes(byteArrayOutputStream.toByteArray());
            }
        }
    }
}
