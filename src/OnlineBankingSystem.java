public class OnlineBankingSystem {
    public void displayMessage(){


            Bank bank = new Bank();

        bank.register("user1", "password1");
        bank.register("user2", "password2");

            // Login
        bank.login("user1", "password1");
        bank.login("user2", "password1");
        bank.login("user3", "password3");


    }
}
