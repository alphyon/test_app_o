package com.elaniin.nitro.core;
import com.google.gson.JsonElement;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

class IntegratorTest {
    static final String TEST_URL = " http://localhost:3000";
    private UtilsProxy integratorProxy = new UtilsProxy();

    @Test
    void test_connect_external_api() {
        boolean result;
        result = integratorProxy.connectUrl(TEST_URL, null, null);

        assertTrue(result);

    }

    @Test
    void test_get_object_json_external_api() throws Exception {

        Object result;
        result = integratorProxy.getResponseFromUrl(TEST_URL,null,null);
        assertThat(result, instanceOf(JsonElement.class));
    }

    @Test
    void test_get_data_json_external_api() throws Exception {

//        JsonElement jsonString = (JsonElement) integratorProxy.getResponseFromUrl(TEST_URL,null,null);
//        JSONObject tp = integratorProxy.generateObjectFromResponse(jsonString);
//        assertNotNull(tp.getString("title"));
    }
}