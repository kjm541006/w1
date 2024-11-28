package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인 적용되어야 함")
    void vip_o(){
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        Assertions.assertThat(discount).isEqualTo(1000);
    }


    @Test
    @DisplayName("VIP가 아니면 10% 할인 x")
    void vip_x(){
        Member memberBasic = new Member(2L, "memberBasic", Grade.BASIC);
        int discount = discountPolicy.discount(memberBasic, 10000);
        Assertions.assertThat(discount).isEqualTo(0);
    }

}