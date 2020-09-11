package AI.Bot.controller;

import org.json.JSONObject;

public class DecryptResponse {
    public static String getResponse(String encrypted){
//        System.out.println(encrypted);
        String jsonResponse = new String(decrypt(new StringBuffer(encrypted).reverse().toString()));
//        System.out.println(jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getString("msg");
    }

    private static byte[] decrypt(String str2) {
        int length = str2.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str2.charAt(i), 16) << 4) + Character.digit(str2.charAt(i + 1), 16));
        }
        return bArr;
    }
}
