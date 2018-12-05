package me.secosme.jwtexample;

import java.util.Optional;
import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.servlet.http.HttpServletRequest;

@ApplicationScoped
public class LoginDTOFactory {

    @Produces
    @RequestScoped
    public Supplier<Optional<LoginDTO>> create(HttpServletRequest req) {
        System.out.println("created request optional of login!");
        return () -> Optional
            .ofNullable(req.getSession(false))
            .map(session -> {
                System.out.println("session exist!");
                return (LoginDTO) session.getAttribute("logged.user");
            });
    }
}
