package dev.mettan.paseto;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FormatUtils {
    public static byte[] decodeHex(String key) {
        List<Integer> integers = StreamSupport.stream(Splitter.fixedLength(2).split(key).spliterator(), false)
                .map(i -> Integer.parseInt(i, 16))
                .collect(Collectors.toList());

        byte[] res = new byte[integers.size()];
        int i = 0;
        for (Integer integer : integers) {
            res[i] = integer.byteValue();
            i++;
        }

        return res;
    }
}
