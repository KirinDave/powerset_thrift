//
//  TServer.cs
//
//  Begin:  Dec 3, 2007
//  Authors: 
//		Will Palmeri <wpalmeri@imeem.com>
//
//  Distributed under the Thrift Software License
//
//  See accompanying file LICENSE or visit the Thrift site at:
//  http://developers.facebook.com/thrift/using

using System;
using System.Collections.Generic;
using Thrift.Protocol;
using Thrift.Transport;

namespace Thrift.Server
{
	public abstract class TServer
	{
		/**
		 * Core processor
		 */
		protected TProcessor processor;

		/**
		 * Server transport
		 */
		protected TServerTransport serverTransport;

		/**
		 * Input Transport Factory
		 */
		protected TTransportFactory inputTransportFactory;

		/**
		 * Output Transport Factory
		 */
		protected TTransportFactory outputTransportFactory;

		/**
		 * Input Protocol Factory
		 */
		protected TProtocolFactory inputProtocolFactory;

		/**
		 * Output Protocol Factory
		 */
		protected TProtocolFactory outputProtocolFactory;

		/**
		 * Default constructors.
		 */

		public TServer(TProcessor processor,
						  TServerTransport serverTransport)
			:this(processor, serverTransport, new TTransportFactory(), new TTransportFactory(), new TBinaryProtocol.Factory(), new TBinaryProtocol.Factory())
		{
		}

		public TServer(TProcessor processor,
						  TServerTransport serverTransport,
						  TTransportFactory transportFactory)
			:this(processor,
				 serverTransport,
				 transportFactory,
				 transportFactory,
				 new TBinaryProtocol.Factory(),
				 new TBinaryProtocol.Factory())
		{
		}

		public TServer(TProcessor processor,
						  TServerTransport serverTransport,
						  TTransportFactory transportFactory,
						  TProtocolFactory protocolFactory)
			:this(processor,
				 serverTransport,
				 transportFactory,
				 transportFactory,
				 protocolFactory,
				 protocolFactory)
		{
		}

		public TServer(TProcessor processor,
						  TServerTransport serverTransport,
						  TTransportFactory inputTransportFactory,
						  TTransportFactory outputTransportFactory,
						  TProtocolFactory inputProtocolFactory,
						  TProtocolFactory outputProtocolFactory)
		{
			this.processor = processor;
			this.serverTransport = serverTransport;
			this.inputTransportFactory = inputTransportFactory;
			this.outputTransportFactory = outputTransportFactory;
			this.inputProtocolFactory = inputProtocolFactory;
			this.outputProtocolFactory = outputProtocolFactory;
		}

		/**
		 * The run method fires up the server and gets things going.
		 */
		public abstract void Serve();
	}
}

