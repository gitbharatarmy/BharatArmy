package com.bharatarmy.Utility;




//public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
//
//    private static final String TAG = "MyFirebaseIIDService";
//


//    @Override
//    public void onTokenRefresh() {
//
//        //Getting registration token
//        String refreshedToken = String.valueOf(FirebaseInstanceId.getInstance().getToken());
//        //getting old saved token
//        String old_token = Utils.getPref(getApplicationContext(), "registration_id");
//
//        if(!old_token.equalsIgnoreCase(refreshedToken)){
//            Utils.setPref(getApplicationContext(), "registration_id", refreshedToken);
//            // sendRegistrationToServer(refreshedToken);
//        }
//        //Displaying token on logcat
//        Log.d(TAG, "Refreshed token from sevice: " + refreshedToken);
//
//    }

//    private void sendRegistrationToServer(String token) {
//        try {
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("StudentID", Utility.getPref(getApplicationContext(), "studid"));
//            hashMap.put("DeviceId", Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
//            hashMap.put("TokenId", token);
//            AddDeviceDetailAsyncTask addDeviceDetailAsyncTask = new AddDeviceDetailAsyncTask(hashMap);
//            boolean result = addDeviceDetailAsyncTask.execute().get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
