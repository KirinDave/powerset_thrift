#!/usr/bin/perl

use strict;
use warnings;

use lib '../../lib/perl/lib';
use lib '../gen-perl';

use Thrift;
use Thrift::BinaryProtocol;
use Thrift::Socket;
use Thrift::BufferedTransport;

use SharedService;
use Calculator;
use shared::Types;
use tutorial::Types;

use Data::Dumper;

my $socket    = new Thrift::Socket('localhost',9090);
my $transport = new Thrift::BufferedTransport($socket,1024,1024);
my $protocol  = new Thrift::BinaryProtocol($transport);
my $client    = new CalculatorClient($protocol);


eval{
    $transport->open();

    $client->ping();
    print "ping()\n";


    my $sum = $client->add(1000000,1);
    print "1+1=$sum\n";

    my $work = new Work();

    $work->{op} = Operation::DIVIDE;
    $work->{num1} = 1;
    $work->{num2} = 0;

    eval {
        $client->calculate(1, $work);
        print "Whoa! We can divide by zero?\n";
    }; if($@) {
        warn "InvalidOperation: ".Dumper($@);
    }

    $work->{op} = Operation::SUBTRACT;
    $work->{num1} = 15;
    $work->{num2} = 10;
    my $diff = $client->calculate(1, $work);
    print "15-10=$diff\n";

    my $log = $client->getStruct(1);
    print "Log: $log->{value}\n";

    $transport->close();

}; if($@){
    warn(Dumper($@));
}
