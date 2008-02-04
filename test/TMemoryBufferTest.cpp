/*
thrift -cpp ThriftTest.thrift
g++ -Wall -g -I../lib/cpp/src -I/usr/local/include/boost-1_33_1 \
  TMemoryBufferTest.cpp gen-cpp/ThriftTest_types.cpp \
  ../lib/cpp/.libs/libthrift.a -o TMemoryBufferTest
./TMemoryBufferTest
*/

#include <iostream>
#include <climits>
#include <cassert>
#include <transport/TTransportUtils.h>
#include <protocol/TBinaryProtocol.h>
#include "gen-cpp/ThriftTest_types.h"


int main(int argc, char** argv) {
  {
    using facebook::thrift::transport::TMemoryBuffer;
    using facebook::thrift::protocol::TBinaryProtocol;
    using boost::shared_ptr;

    shared_ptr<TMemoryBuffer> strBuffer(new TMemoryBuffer());
    shared_ptr<TBinaryProtocol> binaryProtcol(new TBinaryProtocol(strBuffer));

    thrift::test::Xtruct a;
    a.i32_thing = 10;
    a.i64_thing = 30;
    a.string_thing ="holla back a";

    a.write(binaryProtcol.get());
    std::string serialized = strBuffer->getBufferAsString();

    shared_ptr<TMemoryBuffer> strBuffer2(new TMemoryBuffer());
    shared_ptr<TBinaryProtocol> binaryProtcol2(new TBinaryProtocol(strBuffer2));

    strBuffer2->resetBuffer((uint8_t*)serialized.data(), serialized.length());
    thrift::test::Xtruct a2;
    a2.read(binaryProtcol2.get());

    assert(a == a2);
  }

  {
    using facebook::thrift::transport::TMemoryBuffer;
    using std::string;
    using std::cout;
    using std::endl;

    string* str1 = new string("abcd1234");
    const char* data1 = str1->data();
    TMemoryBuffer buf((uint8_t*)str1->data(), str1->length(), TMemoryBuffer::COPY);
    delete str1;
    string* str2 = new string("plsreuse");
    bool obj_reuse = (str1 == str2);
    bool dat_reuse = (data1 == str2->data());
    cout << "Object reuse: " << obj_reuse << "   Data reuse: " << dat_reuse
      << ((obj_reuse && dat_reuse) ? "   YAY!" : "") << endl;
    delete str2;

    string str3 = "wxyz", str4 = "6789";
    buf.readAppendToString(str3, 4);
    buf.readAppendToString(str4, INT_MAX);

    assert(str3 == "wxyzabcd");
    assert(str4 == "67891234");
  }

  {
    using facebook::thrift::transport::TTransportException;
    using facebook::thrift::transport::TMemoryBuffer;
    using std::string;

    char data[] = "foo\0bar";

    TMemoryBuffer buf1((uint8_t*)data, 7, TMemoryBuffer::OBSERVE);
    string str = buf1.getBufferAsString();
    assert(str.length() == 7);
    buf1.resetBuffer();
    try {
      buf1.write((const uint8_t*)"foo", 3);
      assert(false);
    } catch (TTransportException& ex) {}

    TMemoryBuffer buf2((uint8_t*)data, 7, TMemoryBuffer::COPY);
    try {
      buf2.write((const uint8_t*)"bar", 3);
    } catch (TTransportException& ex) {
      assert(false);
    }
  }


}
