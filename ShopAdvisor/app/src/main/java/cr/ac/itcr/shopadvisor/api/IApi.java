package cr.ac.itcr.shopadvisor.api;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Efren on 04/05/2016.
 */
public interface IApi<Object> {

    public boolean Save(Object object);

    public boolean Update(Object object);

    public boolean Delete(Object object);

    public ArrayList<Object> GetAll() throws ExecutionException, InterruptedException;

    public ArrayList<Object> GetBy(Object object);

}