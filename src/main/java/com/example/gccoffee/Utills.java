package com.example.gccoffee;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utills {
    public static UUID toUUID(byte[] bytes){
        var byteBuffer= ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());

    }
    //타임스템프-> 로컬 데이트로 바꿔주는데 널처리 하기위한 함수
    public static LocalDateTime toLocalDate(Timestamp timestamp){
        return timestamp!=null? timestamp.toLocalDateTime():null;
    }
}
