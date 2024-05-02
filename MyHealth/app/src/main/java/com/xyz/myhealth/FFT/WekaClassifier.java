package com.xyz.myhealth.FFT;

public class WekaClassifier {

    public static double classify(Double[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.N4ab510320(i);
        return p;
    }
    static double N4ab510320(Double []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 1;
        } else if (((Double) i[64]).doubleValue() <= 12.753077) {
            p = WekaClassifier.N23f09f681(i);
        } else if (((Double) i[64]).doubleValue() > 12.753077) {
            p = WekaClassifier.N4354200e20(i);
        }
        return p;
    }
    static double N23f09f681(Double []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 45.168707) {
            p = WekaClassifier.N5c5b1e5c2(i);
        } else if (((Double) i[0]).doubleValue() > 45.168707) {
            p = WekaClassifier.N6d4f84c64(i);
        }
        return p;
    }
    static double N5c5b1e5c2(Double []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 0;
        } else if (((Double) i[4]).doubleValue() <= 1.250727) {
            p = 0;
        } else if (((Double) i[4]).doubleValue() > 1.250727) {
            p = WekaClassifier.Nf1931973(i);
        }
        return p;
    }
    static double Nf1931973(Double []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() <= 1.250917) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() > 1.250917) {
            p = 0;
        }
        return p;
    }
    static double N6d4f84c64(Double []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 33.628876) {
            p = WekaClassifier.N4ae35dd85(i);
        } else if (((Double) i[4]).doubleValue() > 33.628876) {
            p = 2;
        }
        return p;
    }
    static double N4ae35dd85(Double []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 303.501007) {
            p = WekaClassifier.N4e4e13266(i);
        } else if (((Double) i[0]).doubleValue() > 303.501007) {
            p = 3;
        }
        return p;
    }
    static double N4e4e13266(Double []i) {
        double p = Double.NaN;
        if (i[28] == null) {
            p = 1;
        } else if (((Double) i[28]).doubleValue() <= 1.872446) {
            p = WekaClassifier.N81d16f17(i);
        } else if (((Double) i[28]).doubleValue() > 1.872446) {
            p = WekaClassifier.N5127783b16(i);
        }
        return p;
    }
    static double N81d16f17(Double []i) {
        double p = Double.NaN;
        if (i[14] == null) {
            p = 1;
        } else if (((Double) i[14]).doubleValue() <= 0.949452) {
            p = 1;
        } else if (((Double) i[14]).doubleValue() > 0.949452) {
            p = WekaClassifier.N1965ca808(i);
        }
        return p;
    }
    static double N1965ca808(Double []i) {
        double p = Double.NaN;
        if (i[29] == null) {
            p = 3;
        } else if (((Double) i[29]).doubleValue() <= 0.310061) {
            p = WekaClassifier.N49b859e99(i);
        } else if (((Double) i[29]).doubleValue() > 0.310061) {
            p = WekaClassifier.N77d1b4db10(i);
        }
        return p;
    }
    static double N49b859e99(Double []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 3;
        } else if (((Double) i[10]).doubleValue() <= 6.264101) {
            p = 3;
        } else if (((Double) i[10]).doubleValue() > 6.264101) {
            p = 0;
        }
        return p;
    }
    static double N77d1b4db10(Double []i) {
        double p = Double.NaN;
        if (i[16] == null) {
            p = 1;
        } else if (((Double) i[16]).doubleValue() <= 0.976979) {
            p = 1;
        } else if (((Double) i[16]).doubleValue() > 0.976979) {
            p = WekaClassifier.N179b8e4e11(i);
        }
        return p;
    }
    static double N179b8e4e11(Double []i) {
        double p = Double.NaN;
        if (i[32] == null) {
            p = 1;
        } else if (((Double) i[32]).doubleValue() <= 0.231329) {
            p = 1;
        } else if (((Double) i[32]).doubleValue() > 0.231329) {
            p = WekaClassifier.N209d67f712(i);
        }
        return p;
    }
    static double N209d67f712(Double []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 3;
        } else if (((Double) i[13]).doubleValue() <= 1.689417) {
            p = 3;
        } else if (((Double) i[13]).doubleValue() > 1.689417) {
            p = WekaClassifier.N2be1151c13(i);
        }
        return p;
    }
    static double N2be1151c13(Double []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 3;
        } else if (((Double) i[8]).doubleValue() <= 8.34494) {
            p = WekaClassifier.N516c1bd714(i);
        } else if (((Double) i[8]).doubleValue() > 8.34494) {
            p = 1;
        }
        return p;
    }
    static double N516c1bd714(Double []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if ((Double) i[0] <= 84.24731) {
            p = 1;
        } else if ((Double) i[0] > 84.24731) {
            p = WekaClassifier.N1ace064a15(i);
        }
        return p;
    }
    static double N1ace064a15(Double []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 1;
        } else if ((Double) i[13] <= 2.401054) {
            p = 1;
        } else if ((Double) i[13] > 2.401054) {
            p = 3;
        }
        return p;
    }
    static double N5127783b16(Double []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if ((Double) i[2] <= 45.103344) {
            p = WekaClassifier.N72cdcef617(i);
        } else if ((Double) i[2] > 45.103344) {
            p = 3;
        }
        return p;
    }
    static double N72cdcef617(Double []i) {
        double p = Double.NaN;
        if (i[26] == null) {
            p = 0;
        } else if ((Double) i[26] <= 2.018554) {
            p = 0;
        } else if ((Double) i[26] > 2.018554) {
            p = WekaClassifier.N434ec1db18(i);
        }
        return p;
    }
    static double N434ec1db18(Double []i) {
        double p = Double.NaN;
        if (i[14] == null) {
            p = 1;
        } else if ((Double) i[14] <= 10.122364) {
            p = 1;
        } else if ((Double) i[14] > 10.122364) {
            p = WekaClassifier.N29d873ae19(i);
        }
        return p;
    }
    static double N29d873ae19(Double []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if ((Double) i[5] <= 23.452149) {
            p = 2;
        } else if ((Double) i[5] > 23.452149) {
            p = 0;
        }
        return p;
    }
    static double N4354200e20(Double []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 3;
        } else if ((Double) i[9] <= 7.989027) {
            p = 3;
        } else if ((Double) i[9] > 7.989027) {
            p = WekaClassifier.N5f72fcec21(i);
        }
        return p;
    }
    static double N5f72fcec21(Double []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 3;
        } else if ((Double) i[0] <= 269.340759) {
            p = 3;
        } else if ((Double) i[0] > 269.340759) {
            p = 2;
        }
        return p;
    }
}