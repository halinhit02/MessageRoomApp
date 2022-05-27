package com.nhom6.messageroomapp.data.model.media;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
