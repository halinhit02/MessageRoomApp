package com.nhom6.messageroomapp.data.model.media;

public interface Packable {
    ByteBuf marshal(ByteBuf out);
}