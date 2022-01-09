package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter         // lombok 라이브러리를 이용한 getter, setter
public class HelloData {
    
    private String username;
    private int age;

}
