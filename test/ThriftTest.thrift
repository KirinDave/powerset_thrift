java_package thrift.test
cpp_namespace thrift.test
ruby_namespace Thrift.Test
perl_package ThriftTest
csharp_namespace Thrift.Test

enum Numberz
{
  ONE = 1,
  TWO,
  THREE,
  FIVE = 5,
  SIX,
  EIGHT = 8
}

typedef i64 UserId

struct Bonk
{
  1: string message,
  2: i32 type
}

struct Xtruct
{
  1:  string string_thing,
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

struct Insanity
{
  1: map<Numberz, UserId> userMap,
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

service ThriftTest
{
  void         testVoid(),
  string       testString(1: string thing),
  byte         testByte(1: byte thing),
  i32          testI32(1: i32 thing),
  i64          testI64(1: i64 thing),
  double       testDouble(1: double thing),
  Xtruct       testStruct(1: Xtruct thing),
  Xtruct2      testNest(1: Xtruct2 thing),
  map<i32,i32> testMap(1: map<i32,i32> thing),
  set<i32>     testSet(1: set<i32> thing),
  list<i32>    testList(1: list<i32> thing),
  Numberz      testEnum(1: Numberz thing),
  UserId       testTypedef(1: UserId thing),

  map<i32,map<i32,i32>> testMapMap(1: i32 hello),

  /* So you think you've got this all worked, out eh? */
  map<UserId, map<Numberz,Insanity>> testInsanity(1: Insanity argument),

  /* Multiple parameters */
  Xtruct testMulti(byte arg0, i32 arg1, i64 arg2, map<i16, string> arg3, Numberz arg4, UserId arg5),

  /* Exception specifier */

  void testException(string arg) throws(Xception err1),

  /* Multiple exceptions specifier */

  Xtruct testMultiException(string arg0, string arg1) throws(Xception err1, Xception2 err2)
}

service SecondService
{
  void blahBlah()
}
