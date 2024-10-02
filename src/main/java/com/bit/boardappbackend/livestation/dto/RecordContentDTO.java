package com.bit.boardappbackend.livestation.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RecordContentDTO {
    private Map<String, RecordInfoDTO> recordList;
}
