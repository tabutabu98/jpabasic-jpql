package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 기본 문법과 쿼리 API
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

//            // 반환 타입
//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            // 반환 타입이 명확하지 않을 때
//            Query query3 = em.createQuery("select m.username, m.age from Member m");


            // 리스트를 저장할 때
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            List<Member> resultList = query.getResultList();
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }

            /**
             * 값 하나를 저장할 때
             * 결과가 정확히 하나가 나와야한다.
             * 결과가 없으면 예외가 터진다!
             * 결과가 둘 이상이여도 예외가 터진다!
             */
//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.id = 10", Member.class);
//            Member resu = query.getSingleResult();
//            // Spring Data JPA -> 결과가 없으면 Null 또는 Optional을 반환한다
//            System.out.println("resu = " + resu);

            /**
             * 파라미터 바인딩 - 이름기준
             * 위치기반은 사용하지 않는것을 권장
             */
//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            query.setParameter("username", "member1");
//            Member singleResult = query.getSingleResult();
//            System.out.println("singleResult = " + singleResult.getUsername());
            // 또는
            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("singleResult = " + result.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}
