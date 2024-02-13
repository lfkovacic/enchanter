package com.enchanter.game.engine.entities.interfaces;

import java.util.List;

import com.enchanter.game.engine.entities.GameObject;

public interface Tileable {
    void addGameObject(GameObject obj);

    void setGameObjects(List<GameObject> gameObjectList);

    List<GameObject> getObjects();

    int getGridWidth();

    void setGridWidth(int gridWidth);

    int getGridHeight();

    void setGridHeight(int gridHeight);

    int getCellWidth();

    void setCellWidth(int cellWidth);

    int getCellHeight();

    void setCellHeight(int cellHeight);

    int getCellsX();

    void setCellsX(int cellsX);

    int getCellsY();

    void setCellsY(int cellsY);
}
