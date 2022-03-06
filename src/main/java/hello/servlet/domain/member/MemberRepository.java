package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// 동시성 문제가 고려되어 있지 않음. 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려


// 회원 저장소
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;
    /* 
        new로 memberrepository를 아무리 많이 만들어도
        static으로 생성된 위 두 변수는 딱 하나만 생성된다
    */

    // 생성자를 private로 만들어 접근을 막는다
    private MemberRepository(){
    }

    // singleton
    // 최초 한번만 new 연산자를 통하여 메모리에 할당
    private static final MemberRepository instance = new MemberRepository();

    // getInstance 메소드를 통해 생성된 객체를 가져옴
    public static MemberRepository getInstance(){
        return instance;
    }

    
    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }
    
    public void clearStore(){
        store.clear();
    }

}
