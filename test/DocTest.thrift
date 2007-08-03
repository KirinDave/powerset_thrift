java_package thrift.test
cpp_namespace thrift.test

// C++ comment
/* c style comment */

# the new unix comment

/** Some doc text goes here.  Wow I am [nesting these] (no more nesting.) */
enum Numberz
{

  /** This is how to document a parameter */
  ONE = 1,

  /** And this is a doc for a parameter that has no specific value assigned */
  TWO,

  THREE,
  FIVE = 5,
  SIX,
  EIGHT = 8
}

/** This is how you would do a typedef doc */
typedef i64 UserId 

/** And this is where you would document a struct */
struct Xtruct
{

  /** And the members of a struct */
  1:  string string_thing

  /** doct text goes before a comma */
  4:  byte   byte_thing,

  9:  i32    i32_thing,
  11: i64    i64_thing
}

struct Xtruct2
{
  1: byte   byte_thing,
  2: Xtruct struct_thing,
  3: i32    i32_thing
}

/** Struct insanity */
struct Insanity
{

  /** This is doc for field 1 */
  1: map<Numberz, UserId> userMap,

  /** And this is doc for field 2 */
  2: list<Xtruct> xtructs 
}

exception Xception {
  1: i32 errorCode,
  2: string message
}

exception Xception2 {
  1: i32 errorCode,
  2: Xtruct struct_thing
}
 
struct EmptyStruct {}

struct OneField {
  1: EmptyStruct field
}

/** This is where you would document a Service */
service ThriftTest
{

  /** And this is how you would document functions in a service */
  void         testVoid(),
  string       testString(1: string thing),
  byte         testByte(1: byte thing),
  i32          testI32(1: i32 thing),

  /** Like this one */
  i64          testI64(1: i64 thing),
  double       testDouble(1: double thing),
  Xtruct       testStruct(1: Xtruct thing),
  Xtruct2      testNest(1: Xtruct2 thing),
  map<i32,i32> testMap(1: map<i32,i32> thing),
  set<i32>     testSet(1: set<i32> thing),
  list<i32>    testList(1: list<i32> thing),

  /** This is an example of a function with params documented */
  Numberz      testEnum(

    /** This param is a thing */
    1: Numberz thing

  ),

  UserId       testTypedef(1: UserId thing),

  map<i32,map<i32,i32>> testMapMap(1: i32 hello),

  /* So you think you've got this all worked, out eh? */
  map<UserId, map<Numberz,Insanity>> testInsanity(1: Insanity argument),

  /* Multiple parameters */
  
  Xtruct	testMulti(byte arg0, i32 arg1, i64 arg2, map<i16, string> arg3, Numberz arg4, UserId arg5),

  /* Exception specifier */

  void testException(string arg) throws(Xception err1),

  /* Multiple exceptions specifier */

  Xtruct testMultiException(string arg0, string arg1) throws(Xception err1, Xception2 err2)
}

/// This style of Doxy-comment doesn't work.
typedef i32 SorryNoGo

/**
 * This is a trivial example of a multiline docstring.
 */
typedef i32 TrivialMultiLine

/**
 * This is the cannonical example
 * of a multiline docstring.
 */
typedef i32 StandardMultiLine

/**
 * The last line is non-blank.
 * I said non-blank! */
typedef i32 LastLine

/** Both the first line
 * are non blank. ;-)
 * and the last line */
typedef i32 FirstAndLastLine

/**
 *    INDENTED TITLE
 * The text is less indented.
 */
typedef i32 IndentedTitle

/**       First line indented.
 * Unfortunately, this does not get indented.
 */
typedef i32 FirstLineIndent


/**
 * void code_in_comment() {
 *   printf("hooray code!");
 * }
 */
typedef i32 CodeInComment

    /**
     * Indented Docstring.
     * This whole docstring is indented.
     *   This line is indented further.
     */
typedef i32 IndentedDocstring

/** Irregular docstring.
 * We will have to punt
  * on this thing */
typedef i32 Irregular1

/**
 * note the space
 * before these lines
* but not this
 * one
 */
typedef i32 Irregular2

/**
* Flush against
* the left.
*/
typedef i32 Flush

/**
  No stars in this one.
  It should still work fine, though.
    Including indenting.
    */
typedef i32 NoStars

/** Trailing whitespace   
Sloppy trailing whitespace   
is truncated.   */
typedef i32 TrailingWhitespace

/**
 * This is a big one.
 *
 * We'll have some blank lines in it.
 * 
 * void as_well_as(some code) {
 *   puts("YEEHAW!");
 * }
 */
typedef i32 BigDog
