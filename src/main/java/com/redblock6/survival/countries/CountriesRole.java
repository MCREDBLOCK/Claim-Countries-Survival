package com.redblock6.survival.countries;

import static com.redblock6.survival.Main.translate;

public enum CountriesRole {
    Leader,
    Resident;

    public static CountriesRole fromString(String role) {
        switch (role) {
            case "Leader":
                return Leader;
            default:
                return Resident;
        }
    }

    public String getPrefix() {
        switch (this) {
            case Leader:
                return translate("&4&lLEADER &f");
            default:
                return "";
        }
    }

}
