package cn.edu.jlu.limf.himsc.controller.AndroidORMController;

import android.database.sqlite.SQLiteDatabase;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.edu.jlu.limf.himsc.models.HeartRateZones;
import cn.edu.jlu.limf.himsc.models.UserHealthRecordBean;

/**
 * Created by merlin on 17-5-6.
 */

public class DBtoJSONController {
    private SQLiteDatabase db;
    private HeartRateZones heartRateZones;
    private UserHealthRecordBean userHealthRecordBean;

    public DBtoJSONController(SQLiteDatabase db) {
        this.db = db;
    }

    public void initialize(){
        heartRateZones=new HeartRateZones();
        userHealthRecordBean=new UserHealthRecordBean();
    }

    public boolean DB2json(SQLiteDatabase db){
        return true;
    }

    public List<UserHealthRecordBean> Select_OP(SQLiteDatabase db){
        List<UserHealthRecordBean> userHealthRecordBeanList = DataSupport.findAll(UserHealthRecordBean.class);
        return userHealthRecordBeanList;
    }
}
