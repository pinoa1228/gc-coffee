package com.example.gccoffee.Model;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDto (
   UUID productId,
   String productName,
   Category category,
  long price,
 String description,
LocalDateTime createdAt,
   LocalDateTime updateAt

           ){}
