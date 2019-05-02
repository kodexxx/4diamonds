package com.example.a4diamonds.entities;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameInfoEntity {
    Boolean playerRedReady;
    Boolean playerBlueReady;
    Date createdAt;
    List<StepEntity> steps;
}
