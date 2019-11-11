package src.tokenizer;

public enum charClass {
    //=====<String Characters>=====//
    kLChar, kUChar, kDigit, kSpace,

    //=====<Block Characters>======//
    kOpenBrace, kCloseBrace, kOpenParen, kCloseParen, kQuote,

    //=========<Operators>=========//
    kOp, kSign, kAsmtOp, kCompOp,

    //===========<Misc>============//
    kDot, kComma, kEOF, kWhiteSpace;

    public static charClass getClass(char in) {
        //=====<String Characters>=====//
        if (in >= 'a' && in <= 'z') {
            return kLChar;
        } else if (in >= 'A' && in <= 'Z') {
            return kUChar;
        } else if (in >= '0' && in <= '9') {
            return kDigit;
        } else if (in == ' ') {
            return kSpace;
        }

        //=====<Block Characters>======//
        else if (in == '{') {
            return kOpenBrace;
        } else if (in == '}') {
            return kCloseBrace;
        } else if (in == '(') {
            return kOpenParen;
        } else if (in == ')') {
            return kCloseParen;
        } else if (in == '"') {
            return kQuote;
        }

        //=========<Operators>=========//
        else if (in == '+' || in == '-') {
            return kSign;
        } else if (in == '*' || in == '/') {    //todo check for more ops
            return kOp;
        } else if (in == '=') {
            return kAsmtOp;
        } else if (in == '<' || in == '>' || in == '!') {   //Todo Check not op
            return kCompOp;
        }

        //===========<Misc>============//
        else if (in == '.') {
            return kDot;
        } else if (in == ',') {
            return kComma;
        } else if (in == '\t' || in == '\n' || in == '\r') {
            return kWhiteSpace;
        } else {
            return kEOF;
        }
    }
}
