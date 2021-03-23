package com.blueur.hashcode.dummy;

import com.blueur.hashcode.common.Executor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DummyApplication {
    public static void main(String[] args) throws Throwable {
        final Executor<List<String>, List<String>> executor = Executor.<List<String>, List<String>>builder()
                .problem("problem/2021-traffic_signaling")
                .parser(RawParser::new)
                .solver(IdentitySolver::new)
                .writer(RawWriter::new)
                .build();
//        executor.execute("a.txt");
        executor.executeAll();
    }
}
