package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /**
         * 경로 표현식
         */
        try {
            Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            /**
             * 상태 필드
             */
//            String query = "select m.username from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * 단일 값 연관 경로
             * m.team.name = 상태 필드
             */
//            String query = "select m.team.name from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * 단일 값 연관 경로
             * 묵시적 내부 조인 발생
             * 조심해서 사용해야함(실무에서)
             */
//            String query = "select m.team from Member m";
//            List<Team> result = em.createQuery(query, Team.class)
//                    .getResultList();
//
//            for (Team s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * 컬렉션 값 연관 경로
             * 탐색 안됨
             * 명시적 조인을 하여 별칭을 만들고 그 별칭으로 탐색
             * 묵시적 조인을 사용하지 말고 명시적 조인으로만 사용(부작용 방지)
             */
            String query = "select m.username from Team t join  t.members m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            System.out.println("result = " + result);

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
