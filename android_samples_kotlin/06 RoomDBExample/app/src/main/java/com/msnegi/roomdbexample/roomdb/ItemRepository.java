package com.msnegi.roomdbexample.roomdb;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        itemDao = db.itemDao();
    }

    public void insert(Item item) {
        itemDao.insert(item);
    }

    public List<Item> getAll(){
        return itemDao.getAll();
    }

    public Item findItemByItemId(String item_id) {
        return itemDao.findItemByItemId(item_id);
    }

    public void updateItem(String item_id, String descripton) {
        itemDao.updateItem(item_id, descripton);
    }

    public void update(Item item) {
        itemDao.update(item);
    }

    public void delete(Item item) {
        itemDao.delete(item);
    }

    public void deleteAll() {
        itemDao.deleteAll();
    }
}
