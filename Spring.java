public class Spring {
    private float power;
    private float ko;
    private int A;

    public void setPower(float power) throws IllegalArgumentException {
        if(power <= 0)
            throw new IllegalArgumentException("Сила должна быть\n больше нуля");
        else
            this.power = power;
    }
    public void setKo(float k) throws IllegalArgumentException {
        if(k < 0 || k > 1)
            throw new IllegalArgumentException("Коэффицент должен быть\n в пределах от 0 до 1");
        else
            ko = k;
    }

    private void setAmplitude(int A) {
        if(A >= x0*(1 - ko))
            this.A = (int) (x0*(1 - ko));
        else
            this.A = A;

    }
    private final int K = 10;
    private final int x0 = 50;

    public Integer Move(double time){
        float mass_spring = 0.1f;
        int x1 = (int) (A * Math.cos(Math.sqrt(this.K / mass_spring) * time));
        return (x0-x1);
    }

    public Spring(float power, float ko)throws IllegalArgumentException{
        try{
            setPower(power);
            setKo(ko);
        }catch (IllegalArgumentException ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
        setAmplitude((int) (this.power/K));
    }
}