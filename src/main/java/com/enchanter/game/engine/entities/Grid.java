package com.enchanter.game.engine.entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.enchanter.game.engine.entities.interfaces.Tileable;

public class Grid implements Tileable {
    private List<GameObject> gameObjectList;
    private int gridWidth, gridHeight;
    private int cellWidth, cellHeight;
    private int cellsX, cellsY;

    public Grid(int _gridWidth, int _gridHeight, int _cellWidth, int _cellHeight) throws IOException {
        setGridWidth(_gridWidth);
        setGridHeight(_gridHeight);
        setCellWidth(_cellWidth);
        setCellHeight(_cellHeight);

        setCellsX(_gridWidth / _cellWidth);
        setCellsY(_gridHeight / _cellHeight);

        gameObjectList = new ArrayList<>();
    }

    @Override
    public void addGameObject(GameObject obj) {
        this.gameObjectList.add(obj);
    }

    @Override
    public void setGameObjects(List<GameObject> gameObjectList) {
        this.gameObjectList = new ArrayList<>(gameObjectList);
    }

    @Override
    public List<GameObject> getObjects() {
        return new ArrayList<>(this.gameObjectList);
    }

    @Override
    public int getGridWidth() {
        return gridWidth;
    }

    @Override
    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    @Override
    public int getGridHeight() {
        return gridHeight;
    }

    @Override
    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    @Override
    public int getCellWidth() {
        return cellWidth;
    }

    @Override
    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    @Override
    public int getCellHeight() {
        return cellHeight;
    }

    @Override
    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    @Override
    public int getCellsX() {
        return cellsX;
    }

    @Override
    public void setCellsX(int cellsX) {
        this.cellsX = cellsX;
    }

    @Override
    public int getCellsY() {
        return cellsY;
    }

    @Override
    public void setCellsY(int cellsY) {
        this.cellsY = cellsY;
    }
}