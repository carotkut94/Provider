package com.death.provider.utils;

/**
 * Created by deathcode on 25/03/17.
 */

public class SelectorAttributes {


    public static String BASE_PAYTM = "https://paytm.com/shop/search?q=";
    public static String BASE_FLIPKART = "https://www.flipkart.com/search?q=";
    public static String BASE_AMAZON = "http://www.amazon.in/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";

    public static String PAYTM_ROOT = "._2i1r";
    public static String PAYTM_NAME_CSS = "._2apC";
    public static String PAYTM_IMAGE_CSS = "img";
    public static String PAYTM_PRICE_CSS = "._1kMS";
    public static String PAYTM_LINK_CSS = "a._8vVO";

    public static String AMAZON_ROOT = "div.s-item-container";
    public static String AMAZON_NAME_CSS = "h2.a-size-base.a-color-null.s-inline.scx-truncate.s-access-title.a-text-normal";
    public static String AMAZON_IMAGE_CSS = "img.s-access-image.cfMarker";
    public static String AMAZON_PRICE_CSS = "span.a-size-base.a-color-price.a-text-bold";
    public static String AMAZON_LINK_CSS = "a.a-link-normal";

//    public static String FLIPKART_NAME_CSS = "._2apC";
//    public static String FLIPKART_IMAGE_CSS = "img";
//    public static String FLIPKART_PRICE_CSS = "._1kMS";
//    public static String FLIPKART_LINK_CSS = "a._8vVO";

    public static String[] getArrayStoresWithQuery(String query)
    {
        return new String[]{BASE_PAYTM+query, BASE_AMAZON+query};
    }

    public static String getStoreName(String link)
    {
        if(link.contains("amazon"))
        {
            return "AMAZON";
        }
        else if(link.contains("flipkart"))
        {
            return  "FLIPKART";
        }
        else if(link.contains("paytm")){
            return  "PAYTM";
        }
        return "DEFAULT";
    }

    public static String[] selectorArray(String storeName)
    {
        if(storeName.equals("PAYTM"))
        {
            return new String[]{PAYTM_NAME_CSS,PAYTM_IMAGE_CSS, PAYTM_PRICE_CSS, PAYTM_LINK_CSS};
        }
        return  new String[]{AMAZON_NAME_CSS, AMAZON_IMAGE_CSS, AMAZON_PRICE_CSS, AMAZON_LINK_CSS};
    }


}
