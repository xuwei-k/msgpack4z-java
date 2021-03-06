package msgpack4z;

import org.msgpack.core.ExtensionTypeHeader;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ValueType;
import java.io.IOException;
import java.math.BigInteger;

public class MsgpackJavaUnpacker implements MsgUnpacker {
    private final MessageUnpacker unpacker;

    public MsgpackJavaUnpacker(MessageUnpacker unpacker) {
        this.unpacker = unpacker;
    }

    public MsgpackJavaUnpacker(MessagePack.UnpackerConfig config, byte[] bytes) {
        this(config.newUnpacker(bytes));
    }

    public static MsgUnpacker defaultUnpacker(byte[] bytes) {
        return new MsgpackJavaUnpacker(new MessagePack.UnpackerConfig(), bytes);
    }

    public static MsgType toMsgType(ValueType t) {
        switch (t) {
            case NIL:
                return MsgType.NIL;
            case BOOLEAN:
                return MsgType.BOOLEAN;
            case INTEGER:
                return MsgType.INTEGER;
            case FLOAT:
                return MsgType.FLOAT;
            case STRING:
                return MsgType.STRING;
            case BINARY:
                return MsgType.BINARY;
            case ARRAY:
                return MsgType.ARRAY;
            case MAP:
                return MsgType.MAP;
            case EXTENSION:
                return MsgType.EXTENSION;
            default:
                throw new RuntimeException("impossible");
        }
    }

    @Override
    public MsgType nextType() throws IOException {
        return toMsgType(unpacker.getNextFormat().getValueType());
    }

    @Override
    public byte unpackByte() throws IOException {
        return unpacker.unpackByte();
    }

    @Override
    public short unpackShort() throws IOException {
        return unpacker.unpackShort();
    }

    @Override
    public int unpackInt() throws IOException {
        return unpacker.unpackInt();
    }

    @Override
    public long unpackLong() throws IOException {
        return unpacker.unpackLong();
    }

    @Override
    public BigInteger unpackBigInteger() throws IOException {
        return unpacker.unpackBigInteger();
    }

    @Override
    public double unpackDouble() throws IOException {
        return unpacker.unpackDouble();
    }

    @Override
    public float unpackFloat() throws IOException {
        return unpacker.unpackFloat();
    }

    @Override
    public int unpackArrayHeader() throws IOException {
        return unpacker.unpackArrayHeader();
    }

    @Override
    public void arrayEnd() throws IOException {
        // do nothing
    }

    @Override
    public void mapEnd() throws IOException {
        // do nothing
    }

    @Override
    public int unpackMapHeader() throws IOException {
        return unpacker.unpackMapHeader();
    }

    @Override
    public boolean unpackBoolean() throws IOException {
        return unpacker.unpackBoolean();
    }

    @Override
    public void unpackNil() throws IOException {
        unpacker.unpackNil();
    }

    @Override
    public String unpackString() throws IOException {
        return unpacker.unpackString();
    }

    @Override
    public byte[] unpackBinary() throws IOException {
        final byte[] bytes = new byte[unpacker.unpackBinaryHeader()];
        unpacker.readPayload(bytes);
        return bytes;
    }

    @Override
    public byte[] readPayload(int length) throws IOException {
        return unpacker.readPayload(length);
    }

    @Override
    public void readPayload(byte[] a) throws IOException {
        unpacker.readPayload(a);
    }

    @Override
    public ExtTypeHeader unpackExtTypeHeader() throws IOException {
        final ExtensionTypeHeader header = unpacker.unpackExtensionTypeHeader();
        return new ExtTypeHeader(header.getType(), header.getLength());
    }

    @Override
    public void close() throws IOException {
        unpacker.close();
    }
}
