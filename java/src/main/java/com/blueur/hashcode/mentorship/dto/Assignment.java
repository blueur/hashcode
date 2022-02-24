package com.blueur.hashcode.mentorship.dto;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Assignment {
    final String id;
    final List<String> contributors;
}
