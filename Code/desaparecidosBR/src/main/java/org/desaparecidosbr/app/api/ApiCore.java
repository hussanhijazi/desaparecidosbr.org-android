package org.desaparecidosbr.app.api;

/**
 * Class containing the main API methods to do the request
 *
 * @author Rafael Decker
 */
public abstract class ApiCore {

//    /**
//     * Base URL.
//     */
//    public static final String BASE_URL = MyConfig.URL_SERVICE;
//
//    /**
//     * Context reference
//     */
//    protected Activity mActivity;
//
//    /**
//     * Interface call-backs
//     */
//    protected ApiListener mApiListener = null;
//
//    /**
//     * Volley Request
//     */
//    protected RequestQueue rq = null;
//
//    private JsonArrayRequest request;
//
//    /**
//     * Constructor
//     *
//     * @param Activity    activity
//     * @param ApiListener apiListener
//     */
//    public ApiCore(Activity activity, ApiListener apiListener) {
//        this.mActivity = activity;
//        this.mApiListener = apiListener;
//    }
//
//    /**
//     * Method that should be called to trigger a request
//     *
//     * @param HashMap <String,String> parameters
//     */
//    public void doRequest(final int operation, HashMap<String, String> parameters) {
//
//        // on pre execute call back called before execute the request
//        if (mApiListener != null) {
//            mApiListener.onPreExecute();
//        }
//
//        // Get a request queue instance by using the volley framework
//        rq = VolleySingleton.getInstance(mActivity)
//                .getRequestQueue();
//
//        // add a new request with a json callback to handle the response
//        String url = getUrl(parameters);
//        if (rq.getCache()
//                .get(url) != null && !rq.getCache().get(url).isExpired()) {
//            // response exists
//            String cachedResponse = new String(
//                    rq.getCache()
//                            .get(url).data);
//            try {
//                handleJsonResponse(operation, new JSONArray(cachedResponse));
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        } else {
//            addRequest(url, operation);
//        }
//    }
//
//    public void addRequest(String url, final int operation) {
//        request = new JsonArrayRequest(url, new Listener<JSONArray>() {
//
//            public void onResponse(JSONArray jsonRoot) {
//                // the request ends listener is called by the
//                // handleJsonResponse
//                // on the subclass
//                handleJsonResponse(operation, jsonRoot);
//            }
//        }, new Response.ErrorListener() {
//
//            public void onErrorResponse(VolleyError error) {
//                // Call-back from Volley if something wrong happens. We
//                // should call the apiListener call-back
////                if (mApiListener != null) {
////                    mApiListener.onRequestEnds(false, new ArrayList<Object>());
////                }
////               Log.d("lnp", VolleySingleton.getMessage(error.getMessage(), mActivity));
//                Toast.makeText(mActivity,
//                        VolleySingleton.getMessage(error.getMessage(), mActivity),
//                        Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                try {
//                    return MySignature.createBasicAuthHeader();
//                } catch (InvalidKeyException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (NoSuchAlgorithmException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//        int socketTimeout = 30;//30 seconds - change to what you want
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        request.setRetryPolicy(policy);
//        rq.add(request);
//    }
//
//    /**
//     * Sub class should implement it to deliver the full URL to the request.
//     *
//     * @param HashMap <String, String> parameters
//     * @return String fullUrl
//     */
//    public abstract String getUrl(HashMap<String, String> parameters);
//
//    /**
//     * Method that will be called to handle the json response from a request
//     *
//     * @param JSONObject jsonRoot
//     */
//    public abstract void handleJsonResponse(int operation, JSONArray jsonRoot);
//
//    /**
//     * Method used to create the final URL. It inserts parameters on the URL.
//     *
//     * @param String  baseUrl
//     * @param HashMap <String, String> parameters
//     * @return String finalUrl
//     */
//    protected String createUrl(String baseUrl, HashMap<String, String> parameters) {
//
//        String finalUrl = baseUrl;
//
//        // Inserting the parameters on the url
//        if (parameters != null) {
//            if (parameters.size() > 0) {
//                finalUrl += "?";
//
//                Set<String> keysSet = parameters.keySet();
//                int count = 0;
//                int nKeys = keysSet.size();
//                for (String key : keysSet) {
//                    String value = null;
//                    try {
//                        value = URLEncoder.encode(parameters.get(key), "UTF-8");
//                    } catch (Exception e) {
//                        value = parameters.get(key);
//                    }
//
//                    finalUrl += key + "=" + value;
//
//                    boolean isNotTheLastParameter = (count != nKeys - 1);
//                    if (isNotTheLastParameter) {
//                        finalUrl += "&";
//                    }
//                    count++;
//                }
//            }
//        }
//
//        // Used to encode the parameters of the URL, because it may have some
//        // special character
//        try {
//            URL url = new URL(finalUrl);
//            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
//                    url.getQuery(), url.getRef());
//            url = uri.toURL();
//            finalUrl = url.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.i("lnp", finalUrl);
//        return finalUrl;
//    }
}
