package com.infinium.api;

public class Routes {
    
    // Base URL is read from Config, but specific paths are here
    public static String post_case_url = "/cases";
    public static String get_case_url = "/cases/{id}";
    public static String update_case_url = "/cases/{id}";
    public static String delete_case_url = "/cases/{id}";
    
    // External API routes
    public static String get_fetch_billers_url = "https://stage-api.getstan.app/store-shop-us/api/v1/inventory/euronet/list-billers";
}
