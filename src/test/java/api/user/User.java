package api.user;

/**
 * Адаптер-модель api.user.User, делегирует полям из api.User.
 * Нужен для обратной совместимости — если где-то импортируют api.user.User.
 */
public class User extends api.User {
    public User() { super(); }
    public User(String email, String password, String name) { super(email, password, name); }
    public User(String email, String password) { super(email, password); }
}
