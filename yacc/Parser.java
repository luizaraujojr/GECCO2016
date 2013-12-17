//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 12 "expressao.y"
typedef union
{
	char 		id[MAXID + 1];
	double		valor;
	EXPRESSAO	*expressao;
	void		*papel;
} YYSTYPE;
//#line 25 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short MIN=257;
public final static short MAX=258;
public final static short IF=259;
public final static short AND=260;
public final static short OR=261;
public final static short NOT=262;
public final static short ID=263;
public final static short CONST=264;
public final static short LOOKUP=265;
public final static short L_PARENT=266;
public final static short R_PARENT=267;
public final static short ASTER=268;
public final static short PLUS=269;
public final static short COMMA=270;
public final static short MINUS=271;
public final static short DIV=272;
public final static short POTENC=273;
public final static short LE=274;
public final static short LT=275;
public final static short DIF=276;
public final static short GT=277;
public final static short GE=278;
public final static short EQUAL=279;
public final static short SEMICOLON=280;
public final static short NORMAL=281;
public final static short UNIFORM=282;
public final static short BOUNDED=283;
public final static short ROUND=284;
public final static short LN=285;
public final static short EXP=286;
public final static short SMOOTH=287;
public final static short DELAY3=288;
public final static short DT=289;
public final static short TIME=290;
public final static short SPEC=291;
public final static short L_CHAVE=292;
public final static short R_CHAVE=293;
public final static short POINT=294;
public final static short GROUPSUM=295;
public final static short GROUPMAX=296;
public final static short GROUPMIN=297;
public final static short BETAPERT=298;
public final static short L_COLCH=299;
public final static short R_COLCH=300;
public final static short COUNT=301;
public final static short BOUND=302;
public final static short SELECT=303;
public final static short UMINUS=304;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    1,    2,
    2,    2,    2,    2,
};
final static short yylen[] = {                            2,
    1,    1,    3,    3,    3,    3,    2,    3,    3,    3,
    3,    3,    3,    3,    4,    4,    4,    8,    4,    4,
    4,    8,    6,    6,   10,    3,    4,    4,    6,    6,
    1,    1,    4,    6,    6,    6,    3,    3,    1,    1,
    3,    3,    6,    6,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   31,   32,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    7,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    8,    0,    0,    0,    0,    0,
    0,    0,   40,    0,    0,    0,    0,   42,    0,    0,
    0,    0,    0,    0,    0,   26,    0,    0,    0,    0,
    0,    0,    0,   20,    0,   19,    0,   16,   17,   15,
    0,    0,    0,   21,   27,   28,    0,    0,    0,    0,
    0,    0,    0,   33,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,   23,   24,   29,   30,   34,   35,   36,    0,
   43,   44,    0,    0,    0,   18,    0,   22,    0,   25,
};
final static short yydgoto[] = {                         67,
   68,   30,
};
final static short yysindex[] = {                      -232,
 -263, -262, -245, -244, -243, -221,    0,    0, -220, -232,
 -232, -219, -215, -206, -198, -169, -168, -167,    0,    0,
 -156, -126, -118, -117, -255, -116, -113, -112, -196, -124,
 -232, -232, -232, -232, -232, -232, -108, -183,    0, -232,
 -232, -232, -232, -232, -232, -232, -261, -261, -261, -232,
 -104, -261, -261, -261, -232, -232, -232, -232, -232, -232,
 -232, -232, -232, -232, -232,  -80, -196, -208, -140, -154,
 -139, -138, -100,  -63,    0,  255,  267,  -87,  -74,  -39,
  279,  291,    0, -270, -257, -254,  303,    0, -258, -251,
 -250,  -64, -164, -164,  -64,    0, -166, -166, -166, -166,
 -166, -166,    0,    0, -232,    0, -232,    0,    0,    0,
 -232, -232, -232,    0,    0,    0, -232, -232,  -53,  -52,
  -48,  -47, -232,    0,  -46, -232, -196,  315,  327,  -12,
  165,  178,  191,  -45,    0,  -44,  -41,  339,  -36,  204,
 -232, -232,    0,    0,    0,    0,    0,    0,    0, -232,
    0,    0,  217,  351,  230,    0, -232,    0,  243,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -134,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   35,   61,   74,   48,    0,   87,  100,  113,  126,
  139,  152,   14,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -132,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                       101,
  -17,  -42,
};
final static int YYTABLESIZE=630;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        119,
    2,   83,   31,   32,   84,   85,   86,   51,  124,   89,
   90,   91,  121,   37,   69,  122,   71,   72,  125,  126,
   33,   34,   35,  120,    1,    2,    3,    4,    5,    6,
    7,    8,    9,   10,    5,  120,  120,   25,   11,  120,
   27,   28,  120,  120,   36,   37,   40,    6,   12,   13,
   41,   14,   15,   16,   17,   18,   19,   20,  104,   42,
    3,  105,   21,   22,   23,   24,   25,   43,   26,   27,
   28,   55,   56,    4,   57,   58,   59,   60,   61,   62,
   63,   64,   65,   75,   55,   56,   14,   57,   58,   59,
   60,   61,   62,   63,   64,   65,   44,   45,   46,   13,
   29,   55,   56,   55,   57,   58,   59,   58,   59,   47,
   38,   39,   10,   55,   56,  107,   57,   58,   59,   60,
   61,   62,   63,   64,   65,   11,  106,  108,  109,  105,
  105,  105,   39,   70,   38,   39,   73,   38,   12,   48,
   76,   77,   78,   79,   80,   81,   82,   49,   50,   52,
   87,    9,   53,   54,   74,   92,   93,   94,   95,   96,
   97,   98,   99,  100,  101,  102,  110,   55,   56,   66,
   57,   58,   59,   60,   61,   62,   63,   64,   65,  114,
   55,   56,  103,   57,   58,   59,   60,   61,   62,   63,
   64,   65,  115,   55,   56,   88,   57,   58,   59,   60,
   61,   62,   63,   64,   65,  127,  111,  128,   59,  134,
  135,  129,  130,  131,  136,  137,  139,  132,  133,    0,
    0,  147,  148,  138,    0,  149,  140,  116,   55,   56,
  151,   57,   58,   59,   60,   61,   62,   63,   64,   65,
    0,  153,  154,    0,    0,    0,    0,    0,    0,    0,
  155,    0,    0,    0,  143,   55,   56,  159,   57,   58,
   59,   60,   61,   62,   63,   64,   65,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
   37,   37,   37,   37,   37,   37,   37,   37,   37,   37,
   37,   37,   37,    0,   40,    0,    0,    0,    0,    0,
    0,    5,    5,    5,    5,    5,    5,   41,    5,    5,
    5,    5,    5,    5,    6,    6,    6,    6,    6,    6,
    0,    6,    6,    6,    6,    6,    6,    3,    0,    3,
    3,    3,    0,    0,    3,    3,    3,    3,    3,    3,
    4,    0,    4,    4,    4,    0,    0,    4,    4,    4,
    4,    4,    4,   14,    0,    0,   14,    0,    0,    0,
   14,   14,   14,   14,   14,   14,   13,    0,    0,   13,
    0,    0,    0,   13,   13,   13,   13,   13,   13,   10,
    0,    0,   10,    0,    0,    0,   10,   10,   10,   10,
   10,   10,   11,    0,    0,   11,    0,    0,    0,   11,
   11,   11,   11,   11,   11,   12,    0,    0,   12,    0,
    0,    0,   12,   12,   12,   12,   12,   12,    9,    0,
    0,    9,    0,    0,    0,    9,    9,    9,    9,    9,
    9,  144,   55,   56,    0,   57,   58,   59,   60,   61,
   62,   63,   64,   65,  145,   55,   56,    0,   57,   58,
   59,   60,   61,   62,   63,   64,   65,  146,   55,   56,
    0,   57,   58,   59,   60,   61,   62,   63,   64,   65,
  152,   55,   56,    0,   57,   58,   59,   60,   61,   62,
   63,   64,   65,  156,   55,   56,    0,   57,   58,   59,
   60,   61,   62,   63,   64,   65,  158,   55,   56,    0,
   57,   58,   59,   60,   61,   62,   63,   64,   65,  160,
   55,   56,    0,   57,   58,   59,   60,   61,   62,   63,
   64,   65,   55,   56,  112,   57,   58,   59,   60,   61,
   62,   63,   64,   65,   55,   56,  113,   57,   58,   59,
   60,   61,   62,   63,   64,   65,   55,   56,  117,   57,
   58,   59,   60,   61,   62,   63,   64,   65,   55,   56,
  118,   57,   58,   59,   60,   61,   62,   63,   64,   65,
   55,   56,  123,   57,   58,   59,   60,   61,   62,   63,
   64,   65,   55,   56,  141,   57,   58,   59,   60,   61,
   62,   63,   64,   65,   55,   56,  142,   57,   58,   59,
   60,   61,   62,   63,   64,   65,   55,   56,  150,   57,
   58,   59,   60,   61,   62,   63,   64,   65,   55,   56,
  157,   57,   58,   59,   60,   61,   62,   63,   64,   65,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        270,
    0,  263,  266,  266,   47,   48,   49,  263,  267,   52,
   53,   54,  270,    0,   32,  270,   34,   35,  270,  270,
  266,  266,  266,  294,  257,  258,  259,  260,  261,  262,
  263,  264,  265,  266,    0,  294,  294,  299,  271,  294,
  302,  303,  294,  294,  266,  266,  266,    0,  281,  282,
  266,  284,  285,  286,  287,  288,  289,  290,  267,  266,
    0,  270,  295,  296,  297,  298,  299,  266,  301,  302,
  303,  268,  269,    0,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  267,  268,  269,    0,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  266,  266,  266,    0,
    0,  268,  269,  268,  271,  272,  273,  272,  273,  266,
   10,   11,    0,  268,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,    0,  267,  267,  267,  270,
  270,  270,  267,   33,  267,  270,   36,  270,    0,  266,
   40,   41,   42,   43,   44,   45,   46,  266,  266,  266,
   50,    0,  266,  266,  263,   55,   56,   57,   58,   59,
   60,   61,   62,   63,   64,   65,  267,  268,  269,  294,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  267,
  268,  269,  263,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  267,  268,  269,  300,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  105,  270,  107,  273,  263,
  263,  111,  112,  113,  263,  263,  263,  117,  118,   -1,
   -1,  267,  267,  123,   -1,  267,  126,  267,  268,  269,
  267,  271,  272,  273,  274,  275,  276,  277,  278,  279,
   -1,  141,  142,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  150,   -1,   -1,   -1,  267,  268,  269,  157,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  267,  268,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  267,  268,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,   -1,  294,   -1,   -1,   -1,   -1,   -1,
   -1,  267,  268,  269,  270,  271,  272,  294,  274,  275,
  276,  277,  278,  279,  267,  268,  269,  270,  271,  272,
   -1,  274,  275,  276,  277,  278,  279,  267,   -1,  269,
  270,  271,   -1,   -1,  274,  275,  276,  277,  278,  279,
  267,   -1,  269,  270,  271,   -1,   -1,  274,  275,  276,
  277,  278,  279,  267,   -1,   -1,  270,   -1,   -1,   -1,
  274,  275,  276,  277,  278,  279,  267,   -1,   -1,  270,
   -1,   -1,   -1,  274,  275,  276,  277,  278,  279,  267,
   -1,   -1,  270,   -1,   -1,   -1,  274,  275,  276,  277,
  278,  279,  267,   -1,   -1,  270,   -1,   -1,   -1,  274,
  275,  276,  277,  278,  279,  267,   -1,   -1,  270,   -1,
   -1,   -1,  274,  275,  276,  277,  278,  279,  267,   -1,
   -1,  270,   -1,   -1,   -1,  274,  275,  276,  277,  278,
  279,  267,  268,  269,   -1,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  267,  268,  269,   -1,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  267,  268,  269,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  267,  268,  269,   -1,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  267,  268,  269,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  267,  268,  269,   -1,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  267,
  268,  269,   -1,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  268,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  268,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  268,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  268,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  268,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  268,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  268,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  268,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  268,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
};
}
final static short YYFINAL=29;
final static short YYMAXTOKEN=304;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"MIN","MAX","IF","AND","OR","NOT","ID","CONST","LOOKUP",
"L_PARENT","R_PARENT","ASTER","PLUS","COMMA","MINUS","DIV","POTENC","LE","LT",
"DIF","GT","GE","EQUAL","SEMICOLON","NORMAL","UNIFORM","BOUNDED","ROUND","LN",
"EXP","SMOOTH","DELAY3","DT","TIME","SPEC","L_CHAVE","R_CHAVE","POINT",
"GROUPSUM","GROUPMAX","GROUPMIN","BETAPERT","L_COLCH","R_COLCH","COUNT","BOUND",
"SELECT","UMINUS",
};
final static String yyrule[] = {
"$accept : expr",
"expr : CONST",
"expr : ID",
"expr : expr PLUS expr",
"expr : expr MINUS expr",
"expr : expr ASTER expr",
"expr : expr DIV expr",
"expr : MINUS expr",
"expr : L_PARENT expr R_PARENT",
"expr : expr EQUAL expr",
"expr : expr DIF expr",
"expr : expr GT expr",
"expr : expr GE expr",
"expr : expr LT expr",
"expr : expr LE expr",
"expr : NOT L_PARENT expr R_PARENT",
"expr : AND L_PARENT params R_PARENT",
"expr : OR L_PARENT params R_PARENT",
"expr : IF L_PARENT expr COMMA expr COMMA expr R_PARENT",
"expr : MAX L_PARENT params R_PARENT",
"expr : MIN L_PARENT params R_PARENT",
"expr : ROUND L_PARENT expr R_PARENT",
"expr : BETAPERT L_PARENT expr COMMA expr COMMA expr R_PARENT",
"expr : NORMAL L_PARENT expr COMMA expr R_PARENT",
"expr : UNIFORM L_PARENT expr COMMA expr R_PARENT",
"expr : LOOKUP L_PARENT ID COMMA expr COMMA expr COMMA expr R_PARENT",
"expr : expr POTENC expr",
"expr : LN L_PARENT expr R_PARENT",
"expr : EXP L_PARENT expr R_PARENT",
"expr : SMOOTH L_PARENT expr COMMA expr R_PARENT",
"expr : DELAY3 L_PARENT expr COMMA expr R_PARENT",
"expr : DT",
"expr : TIME",
"expr : COUNT L_PARENT objset R_PARENT",
"expr : GROUPSUM L_PARENT objset COMMA ID R_PARENT",
"expr : GROUPMAX L_PARENT objset COMMA ID R_PARENT",
"expr : GROUPMIN L_PARENT objset COMMA ID R_PARENT",
"expr : objset POINT ID",
"params : params COMMA expr",
"params : expr",
"objset : ID",
"objset : objset POINT ID",
"objset : L_COLCH ID R_COLCH",
"objset : BOUND L_PARENT objset COMMA ID R_PARENT",
"objset : SELECT L_PARENT objset COMMA expr R_PARENT",
};

//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 88 "expressao.y"
{
		yyval.expressao = constroi_no_constante (val_peek(0).valor);
	}
break;
case 2:
//#line 92 "expressao.y"
{
		yyval.expressao = constroi_no_variavel (val_peek(0).id);
	}
break;
case 3:
//#line 96 "expressao.y"
{
		yyval.expressao = constroi_no (OP_SOMA, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 4:
//#line 100 "expressao.y"
{
		yyval.expressao = constroi_no (OP_SUBTRACAO, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 5:
//#line 104 "expressao.y"
{
		yyval.expressao = constroi_no (OP_PRODUTO, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 6:
//#line 108 "expressao.y"
{
		yyval.expressao = constroi_no (OP_DIVISAO, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 7:
//#line 112 "expressao.y"
{
		yyval.expressao = constroi_no (OP_UMINUS, val_peek(0).expressao, NULL);
	}
break;
case 8:
//#line 116 "expressao.y"
{
		yyval.expressao = constroi_no (OP_PARENTESES, val_peek(1).expressao, NULL);
	}
break;
case 9:
//#line 120 "expressao.y"
{
		yyval.expressao = constroi_no (OP_IGUAL, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 10:
//#line 124 "expressao.y"
{
		yyval.expressao = constroi_no (OP_DIFERENTE, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 11:
//#line 128 "expressao.y"
{
		yyval.expressao = constroi_no (OP_MAIOR, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 12:
//#line 132 "expressao.y"
{
		yyval.expressao = constroi_no (OP_MAIORIG, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 13:
//#line 136 "expressao.y"
{
		yyval.expressao = constroi_no (OP_MENOR, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 14:
//#line 140 "expressao.y"
{
		yyval.expressao = constroi_no (OP_MENORIG, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 15:
//#line 144 "expressao.y"
{
		yyval.expressao = constroi_no (OP_NOT, val_peek(1).expressao, NULL);
	}
break;
case 16:
//#line 148 "expressao.y"
{
		yyval.expressao = constroi_no (OP_AND, val_peek(1).expressao, NULL);
	}
break;
case 17:
//#line 152 "expressao.y"
{
		yyval.expressao = constroi_no (OP_OR, val_peek(1).expressao, NULL);
	}
break;
case 18:
//#line 156 "expressao.y"
{
		yyval.expressao = constroi_no (OP_COMMA, val_peek(3).expressao, val_peek(1).expressao);
		yyval.expressao = constroi_no (OP_COMMA, val_peek(5).expressao, yyval.expressao);
		yyval.expressao = constroi_no (OP_IF, yyval.expressao, NULL);
	}
break;
case 19:
//#line 162 "expressao.y"
{
		yyval.expressao = constroi_no (OP_MAX, val_peek(1).expressao, NULL);
	}
break;
case 20:
//#line 166 "expressao.y"
{
		yyval.expressao = constroi_no (OP_MIN, val_peek(1).expressao, NULL);
	}
break;
case 21:
//#line 170 "expressao.y"
{
		yyval.expressao = constroi_no (OP_ROUND, val_peek(1).expressao, NULL);
	}
break;
case 22:
//#line 174 "expressao.y"
{
		yyval.expressao = constroi_no (OP_COMMA, val_peek(3).expressao, val_peek(1).expressao);
		yyval.expressao = constroi_no (OP_BETAPERT, val_peek(5).expressao, yyval.expressao);
	}
break;
case 23:
//#line 179 "expressao.y"
{
		yyval.expressao = constroi_no (OP_NORMAL, val_peek(3).expressao, val_peek(1).expressao);
	}
break;
case 24:
//#line 183 "expressao.y"
{
		yyval.expressao = constroi_no (OP_UNIFORM, val_peek(3).expressao, val_peek(1).expressao);
	}
break;
case 25:
//#line 187 "expressao.y"
{
		EXPRESSAO *e;
		e = constroi_no_variavel (val_peek(7).id);
		yyval.expressao = constroi_no (OP_COMMA, val_peek(3).expressao, val_peek(1).expressao);
		yyval.expressao = constroi_no (OP_COMMA, val_peek(5).expressao, yyval.expressao);
		yyval.expressao = constroi_no (OP_COMMA, e, yyval.expressao);
		yyval.expressao = constroi_no (OP_LOOKUP, yyval.expressao, NULL);
	}
break;
case 26:
//#line 196 "expressao.y"
{
		yyval.expressao = constroi_no (OP_POTENC, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 27:
//#line 200 "expressao.y"
{
		yyval.expressao = constroi_no (OP_LOGN, val_peek(1).expressao, NULL);
	}
break;
case 28:
//#line 204 "expressao.y"
{
		yyval.expressao = constroi_no (OP_EXPN, val_peek(1).expressao, NULL);
	}
break;
case 29:
//#line 208 "expressao.y"
{
		yyval.expressao = constroi_no_complemento (OP_SMOOTH, val_peek(3).expressao, val_peek(1).expressao);
	}
break;
case 30:
//#line 212 "expressao.y"
{
		yyval.expressao = constroi_no_complemento (OP_DELAY3, val_peek(3).expressao, val_peek(1).expressao);
	}
break;
case 31:
//#line 216 "expressao.y"
{
		yyval.expressao = constroi_no (OP_DT, NULL, NULL);
	}
break;
case 32:
//#line 220 "expressao.y"
{
		yyval.expressao = constroi_no (OP_TIME, NULL, NULL);
	}
break;
case 33:
//#line 224 "expressao.y"
{
		yyval.expressao = constroi_no (OP_COUNT, val_peek(1).expressao, NULL);
	}
break;
case 34:
//#line 228 "expressao.y"
{
		EXPRESSAO *e1 = constroi_no_variavel (val_peek(1).id);
		yyval.expressao = constroi_no (OP_GRUPO_SOMA, val_peek(3).expressao, e1);
	}
break;
case 35:
//#line 233 "expressao.y"
{
		EXPRESSAO *e1 = constroi_no_variavel (val_peek(1).id);
		yyval.expressao = constroi_no (OP_GRUPO_MAX, val_peek(3).expressao, e1);
	}
break;
case 36:
//#line 238 "expressao.y"
{
		EXPRESSAO *e1;
		e1 = constroi_no_variavel (val_peek(1).id);
		yyval.expressao = constroi_no (OP_GRUPO_MIN, val_peek(3).expressao, e1);
	}
break;
case 37:
//#line 244 "expressao.y"
{
		EXPRESSAO *e1 = constroi_no_variavel (val_peek(2).id);
		EXPRESSAO *e2 = constroi_no_variavel (val_peek(0).id);
		yyval.expressao = constroi_no (OP_PONTO, e1, e2);
	}
break;
case 38:
//#line 252 "expressao.y"
{
		yyval.expressao = constroi_no (OP_COMMA, val_peek(2).expressao, val_peek(0).expressao);
	}
break;
case 39:
//#line 256 "expressao.y"
{
		yyval.expressao = val_peek(0).expressao;
	}
break;
case 40:
//#line 262 "expressao.y"
{
		yyval.expressao = constroi_no_variavel (val_peek(0).id);
	}
break;
case 41:
//#line 266 "expressao.y"
{
		EXPRESSAO *e1 = constroi_no_variavel (val_peek(0).id);
		yyval.expressao = constroi_no (OP_PONTO, val_peek(2).expressao, e1);
	}
break;
case 42:
//#line 271 "expressao.y"
{
		EXPRESSAO *e1 = constroi_no_variavel (val_peek(1).id);
		yyval.expressao = constroi_no (OP_CLSSET, e1, NULL);
	}
break;
case 43:
//#line 276 "expressao.y"
{
		EXPRESSAO *e1 = constroi_no_variavel (val_peek(1).id);
		yyval.expressao = constroi_no (OP_BOUND, val_peek(3).expressao, e1);
	}
break;
case 44:
//#line 281 "expressao.y"
{
		yyval.expressao = constroi_no (OP_SELECT, val_peek(3).expressao, val_peek(1).expressao);
	}
break;
//#line 859 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
