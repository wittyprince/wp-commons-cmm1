package io.github.wittyprince.commons.cmm1.component;

import io.github.wittyprince.commons.cmm1.component.bcrypt.WpBCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * BCryptComponent
 *
 * @author WangChen
 * Created on 2024/10/30
 * @since 0.1
 */
@Component
public class BCryptComponent {

    @Bean
    public WpBCryptPasswordEncoder passwordEncoder() {
        return new WpBCryptPasswordEncoder();
    }

}
