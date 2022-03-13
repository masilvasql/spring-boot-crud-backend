package com.marcelo.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static List<Integer> decodeIntList(String args){

        List<Integer> result = Arrays.asList(args.split(","))
                .stream()
                .map(valor -> Integer.parseInt(valor))
                .collect(Collectors.toList());

        return result;
    }

    public static String decodeParam(String s){
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}
