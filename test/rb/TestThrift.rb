#!/usr/bin/env ruby

$:.push('gen-rb')
$:.push('../../lib/rb/lib')

require 'thrift/transport/tsocket'
require 'thrift/protocol/tbinaryprotocol'
require 'thrift/server/tserver'
require 'ThriftTest'
require 'TestHandler'

require 'rubygems'
require 'test/unit'

class TestThrift < Test::Unit::TestCase

  @@INIT = nil

  def setup
    if @@INIT.nil?
      # Initialize the server
      @handler   = TestHandler.new()
      @processor = Thrift::Test::ThriftTest::Processor.new(@handler)
      @transport = TServerSocket.new(9090)
      @server    = TThreadedServer.new(@processor, @transport)

      @thread    = Thread.new { @server.serve }

      # And the Client
      @socket   = TSocket.new('localhost', 9090)
      @protocol = TBinaryProtocol.new(@socket)
      @client   = Thrift::Test::ThriftTest::Client.new(@protocol)
      @socket.open
    end
  end

  def test_string
    assert_equal(@client.testString('string'), 'string')
  end

  def test_byte
    val = 8
    assert_equal(@client.testByte(val), val)
    assert_equal(@client.testByte(-val), -val)
  end

  def test_i32
    val = 32
    assert_equal(@client.testI32(val), val)
    assert_equal(@client.testI32(-val), -val)
  end

  def test_i64
    val = 64
    assert_equal(@client.testI64(val), val)
    assert_equal(@client.testI64(-val), -val)
  end

  def test_double
    val = 3.14
    assert_equal(@client.testDouble(val), val)
    assert_equal(@client.testDouble(-val), -val)
    assert_kind_of(Float, @client.testDouble(val))
  end

  def test_map
    val = {1 => 1, 2 => 2, 3 => 3}
    assert_equal(@client.testMap(val), val)
    assert_kind_of(Hash, @client.testMap(val))
  end

  def test_list
    val = [1,2,3,4,5]
    assert_equal(@client.testList(val), val)
    assert_kind_of(Array, @client.testList(val))
  end

  def test_enum
    val = Thrift::Test::Numberz::SIX
    ret = @client.testEnum(val)

    assert_equal(ret, 6)
    assert_kind_of(Fixnum, ret)
  end

  def test_typedef
    #UserId  testTypedef(1: UserId thing),
    true
  end

  def test_set
    val = {1 => true, 2 => true, 3 => true}
    assert_equal(@client.testSet(val), val)
    assert_kind_of(Hash, @client.testSet(val))
  end

  def get_struct
    Thrift::Test::Xtruct.new({'string_thing' => 'hi!', 'i32_thing' => 4 })
  end

  def test_struct
    ret = @client.testStruct(get_struct)

    assert_nil(ret.byte_thing, nil)
    assert_nil(ret.i64_thing, nil)
    assert_equal(ret.string_thing, 'hi!')
    assert_equal(ret.i32_thing, 4)
    assert_kind_of(Thrift::Test::Xtruct, ret)
  end

  def test_nest
    struct2 = Thrift::Test::Xtruct2.new({'struct_thing' => get_struct, 'i32_thing' => 10})

    ret = @client.testNest(struct2)

    assert_nil(ret.struct_thing.byte_thing, nil)
    assert_nil(ret.struct_thing.i64_thing, nil)
    assert_equal(ret.struct_thing.string_thing, 'hi!')
    assert_equal(ret.struct_thing.i32_thing, 4)
    assert_equal(ret.i32_thing, 10)

    assert_kind_of(Thrift::Test::Xtruct, ret.struct_thing)
    assert_kind_of(Thrift::Test::Xtruct2, ret)
  end

  def test_insane
    insane = Thrift::Test::Insanity.new({
      'userMap' => { Thrift::Test::Numberz::ONE => 44 },
      'xtructs' => [get_struct,
        Thrift::Test::Xtruct.new({
          'string_thing' => 'hi again',
          'i32_thing' => 12
        })
      ]
    })

    ret = @client.testInsanity(insane)

    assert_not_nil(ret[44])
    assert_not_nil(ret[44][1])

    struct = ret[44][1]

    assert_equal(struct.userMap[Thrift::Test::Numberz::ONE], 44)
    assert_equal(struct.xtructs[1].string_thing, 'hi again')
    assert_equal(struct.xtructs[1].i32_thing, 12)

    assert_kind_of(Hash, struct.userMap)
    assert_kind_of(Array, struct.xtructs)
    assert_kind_of(Thrift::Test::Insanity, struct)
  end

  def test_map_map
    ret = @client.testMapMap(4)
    assert_kind_of(Hash, ret)
    assert_equal(ret, { 4 => { 4 => 4}})
  end

  def test_exception
    assert_raise Thrift::Test::Xception do
      @client.testException('foo')
    end
  end

  def teardown
  end

end
