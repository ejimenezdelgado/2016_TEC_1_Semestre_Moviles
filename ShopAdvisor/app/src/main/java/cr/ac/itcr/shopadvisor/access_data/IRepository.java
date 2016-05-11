package cr.ac.itcr.shopadvisor.access_data;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Efren on 29/3/2016.
 */
public interface IRepository<Object> {


    public boolean Save(Object object);

    public boolean Update(Object object);

    public boolean Delete(Object object);

    public ArrayList<Object> GetAll();

    public ArrayList<Object> GetBy(Object object);
}
