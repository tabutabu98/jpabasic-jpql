package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /**
         * JPQL 함수
         */
        try {
            Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

            /**
             * concat
             */
//            String query = "select concat('a', 'b') from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * substring
             */
//            String query = "select substring(m.username, 2, 3) from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * locate
             */
//            String query = "select locate('de', 'abcdegf') from Member m";
//            List<Integer> result = em.createQuery(query, Integer.class)
//                    .getResultList();
//
//            for (Integer s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * size
             */
//            String query = "select size(t.members) from Team t";
//            List<Integer> result = em.createQuery(query, Integer.class)
//                    .getResultList();
//
//            for (Integer s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * index
             * @OrderColumn, 값 타입의 컬렉션의 위치값을 구할 때 사용 가능(안쓰는게 좋음)
             */
//            String query = "select size(t.members) from Team t";
//            List<Integer> result = em.createQuery(query, Integer.class)
//                    .getResultList();
//
//            for (Integer s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * 사용자 정의 함수 호출
             */
            String query = "select group_concat(m.username) from Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }

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
