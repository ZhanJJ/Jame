package retrofit.interfaces;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/23.
 */
public interface ParamsInterceptor {
    Map checkParams(Map params);
}
