//Decorator

interface Susin {
    String tusindirme();
    double bagasy();
}

class Kofe implements Susin {
    @Override
    public String tusindirme() {
        return "Kofe";
    }

    @Override
    public double bagasy() {
        return 500;
    }
}

class Shay implements Susin {
    @Override
    public String tusindirme() {
        return "Shay";
    }

    @Override
    public double bagasy() {
        return 300;
    }
}

class Latte implements Susin {
    @Override
    public String tusindirme() {
        return "Latte";
    }

    @Override
    public double bagasy() {
        return 600;
    }
}

class Mokko implements Susin {
    @Override
    public String tusindirme() {
        return "Mokko";
    }

    @Override
    public double bagasy() {
        return 700;
    }
}

abstract class SusinDekorator implements Susin {
    protected Susin susin;

    public SusinDekorator(Susin susin) {
        this.susin = susin;
    }

    @Override
    public String tusindirme() {
        return susin.tusindirme();
    }

    @Override
    public double bagasy() {
        return susin.bagasy();
    }
}

class Sut extends SusinDekorator {
    public Sut(Susin susin) {
        super(susin);
    }

    @Override
    public String tusindirme() {
        return susin.tusindirme() + " + Sut";
    }

    @Override
    public double bagasy() {
        return susin.bagasy() + 100;
    }
}

class Qant extends SusinDekorator {
    public Qant(Susin susin) {
        super(susin);
    }

    @Override
    public String tusindirme() {
        return susin.tusindirme() + " + Qant";
    }

    @Override
    public double bagasy() {
        return susin.bagasy() + 50;
    }
}

class Kreme extends SusinDekorator {
    public Kreme(Susin susin) {
        super(susin);
    }

    @Override
    public String tusindirme() {
        return susin.tusindirme() + " + Kreme";
    }

    @Override
    public double bagasy() {
        return susin.bagasy() + 150;
    }
}

class Sirop extends SusinDekorator {
    public Sirop(Susin susin) {
        super(susin);
    }

    @Override
    public String tusindirme() {
        return susin.tusindirme() + " + Sirop";
    }

    @Override
    public double bagasy() {
        return susin.bagasy() + 120;
    }
}

public class Main8 {
    public static void main(String[] args) {
        Susin susin1 = new Kofe();
        susin1 = new Sut(susin1);
        susin1 = new Qant(susin1);
        susin1 = new Kreme(susin1);
        System.out.println(susin1.tusindirme() + " = " + susin1.bagasy() + " tg");

        Susin susin2 = new Latte();
        susin2 = new Sirop(susin2);
        susin2 = new Qant(susin2);
        System.out.println(susin2.tusindirme() + " = " + susin2.bagasy() + " tg");

        Susin susin3 = new Shay();
        susin3 = new Sut(susin3);
        System.out.println(susin3.tusindirme() + " = " + susin3.bagasy() + " tg");
    }
}


//Adapter

interface TolimProcessor {
    void tolimZhasau(double summa);
}

class PayPalTolim implements TolimProcessor {
    @Override
    public void tolimZhasau(double summa) {
        System.out.println("PayPal arqyly tolim zhasaldy: " + summa + " tg");
    }
}

class StripeQyzmeti {
    public void tranzaksiya(double totalSum) {
        System.out.println("Stripe arqyly tranzaksiya oryndaldy: " + totalSum + " tg");
    }
}

class StripeAdapter implements TolimProcessor {
    private StripeQyzmeti stripe;

    public StripeAdapter(StripeQyzmeti stripe) {
        this.stripe = stripe;
    }

    @Override
    public void tolimZhasau(double summa) {
        stripe.tranzaksiya(summa);
    }
}

class KaspiPayQyzmeti {
    public void zhazbaTolim(double aqsha) {
        System.out.println("KaspiPay arqyly tolim oryndaldy: " + aqsha + " tg");
    }
}

class KaspiPayAdapter implements TolimProcessor {
    private KaspiPayQyzmeti kaspi;

    public KaspiPayAdapter(KaspiPayQyzmeti kaspi) {
        this.kaspi = kaspi;
    }

    @Override
    public void tolimZhasau(double summa) {
        kaspi.zhazbaTolim(summa);
    }
}

class InternetDuken {
    public static void main(String[] args) {
        TolimProcessor paypal = new PayPalTolim();
        paypal.tolimZhasau(1500);

        TolimProcessor stripe = new StripeAdapter(new StripeQyzmeti());
        stripe.tolimZhasau(2000);

        TolimProcessor kaspi = new KaspiPayAdapter(new KaspiPayQyzmeti());
        kaspi.tolimZhasau(2500);
    }
}